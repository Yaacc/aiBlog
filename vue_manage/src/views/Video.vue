<template>
  <div>
    <div class="button-left">
      <el-button type="success" icon="el-icon-edit" @click="toPublic">新增</el-button>
      <el-popconfirm
        class="ml10px"
        confirm-button-text="确定"
        cancel-button-text="取消"
        icon="el-icon-info"
        icon-color="red"
        title="确定批量删除这些数据吗？"
        @confirm="delBatch">
        <el-button type="danger" icon="el-icon-delete" slot="reference" plain>批量删除</el-button>
      </el-popconfirm>
    </div>
    <div class="search-input">
      <div class="input-suffix">
        <el-input
          placeholder="输入视频标题"
          v-model="searchTitleName"
          clearable
          style="width: 180px">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <el-button :loading="isLoading" @click="load" type="primary" icon="el-icon-search">搜索</el-button>
      <el-button @click="reset">重置</el-button>
    </div>
    <!--    表格-->
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
<!--      <el-table-column prop="videoCover" label="视频封面" width="80"></el-table-column>-->
      <el-table-column prop="user" label="用户" width="120"></el-table-column>
      <el-table-column prop="videoTitle" label="视频标题" width="100"></el-table-column>
      <el-table-column prop="videoDesc" label="视频简介" width="100"></el-table-column>
      <el-table-column prop="videoLike" label="点赞数" width="100"></el-table-column>
      <el-table-column prop="videoCreated" label="发表时间"></el-table-column>
      <el-table-column label="启用" width="60">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.videoStatus"
                     active-color="#13ce66"
                     inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="预览" align="center">
        <template slot-scope="scope">
          <el-button type="primary"
                     size="mini"
                     plain
                     @click="preview(scope.row)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column prop="action" label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml-5px"
            confirm-button-text="确定"
            cancel-button-text="取消"
            icon="el-icon-info"
            icon-color="red"
            title="确定"
            @confirm="del(scope.row.id)">
            <el-button size="mini" type="danger" slot="reference" plain>删除</el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--    表格-->
    <div>
      <el-dialog title="视频" :visible.sync="dialogFormVisible" width="40%" center>
        <el-form :model="form">
          <el-form-item>
            <h2 style="text-align: center">{{form.videoTitle}}</h2>
            <p style="text-align: center">{{form.user}}</p>
            <!--            <el-input v-model="forms.name" readonly="true" autocomplete="off" ></el-input>-->
          </el-form-item>
          <el-form-item>
            <vue-core-video-player
              :src="form.videoPath"
              :muted="true"
              :autoplay="false"
              :title="form.videoTitle"
              preload="nona"
              :loop="true"
              controls="auto"></vue-core-video-player>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>

    <!--    分页栏-->
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[1,5,10,15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
  </div>
  <!--  main-->
</template>

<script>

export default {
  name: "Video",
  data() {
    return {
      tableData: [],
      total: 0,
      currentPage: 1,
      pageNum: 1,
      pageSize: 10,
      searchTitleName: '', // 搜索框
      dialogFormVisible: false,
      isLoading: false,
      multipleSelection: [],
      formLabelWidth: '80px',
      temp:false,
      form: {
        id:0,
        user: '',
        videoTitle: '',
        videoPath: '',
        videoLike:'',
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/video/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.searchTitleName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.searchTitleName = ""
      this.load()
    },
    // 新增
    toPublic(){
      this.$router.push('/videoPublish')
    },
    // 按序号删除
    del(id){
      this.request.delete("/video/"+id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    // 批量删除
    delBatch(){
      let ids = this.multipleSelection.map(value => value.id)
      this.request.post("/video/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    preview(video){
      this.form.user=video.user
      this.form.id=video.id
      this.form.videoTitle=video.videoTitle
      this.form.videoLike=video.videoLike
      this.form.videoPath=video.videoPath
      this.dialogFormVisible=true
      this.load()
    },

    changeEnable(row){
      this.request.post("/video/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("操作成功")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleDelete(index, row) {
      console.log(index, row);
    },
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`)
      this.pageNum = pageNum
      this.load()
    }
  }
}
</script>

<style scoped>

</style>
