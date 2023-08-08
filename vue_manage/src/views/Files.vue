<template>
  <div>
    <div class="button-left">
      <el-upload action="http://localhost/files/upload"
                 :show-file-list="false"
                 :on-success="handleFileUploadSuccess"
                 style="display: inline-block">
        <el-button type="primary" class="ml10px" icon="el-icon-upload el-icon--right" plain>上传文件</el-button>
      </el-upload>
      <el-popconfirm
        class="ml10px"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        title="您确定批量删除这些数据吗？"
        @confirm="delBatch"
      >
        <el-button type="danger" icon="el-icon-delete" slot="reference" plain>批量删除</el-button>
      </el-popconfirm>

    </div>
    <div class="search-input">
      <div class="input-suffix">
        <el-input
          placeholder="输入文件名称"
          v-model="searchFileName"
          clearable
          style="width: 180px">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <el-button :loading="isLoading" @click="load" type="primary" icon="el-icon-search">搜索</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="文件名称" align="center"></el-table-column>
      <el-table-column prop="type" label="文件类型" align="center"></el-table-column>
      <el-table-column prop="size" label="文件大小(kb)" align="center"></el-table-column>
      <el-table-column label="预览" align="center">
        <template slot-scope="scope">
          <el-button type="primary"
                     size="mini"
                     plain
                     @click="preview(scope.row.url)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column label="下载" align="center">
        <template slot-scope="scope">
          <el-button type="primary"
                     size="mini"
                     plain
                     @click="download(scope.row.url)">下载</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable"
                     active-color="#13ce66"
                     inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml10px"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗？"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger"
                       size="mini"
                       plain
                       slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
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
</template>

<script>
export default {
  name: "Files.vue",
  data(){
    return{
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      currentPage: 1,
      searchFileName: '', // 搜索框
      isLoading:false,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){
      this.request.get("/files/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.searchFileName,
        }

      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.searchFileName = ""
      this.load()
    },

    // 按序号删除
    del(id){
      this.request.delete("/files/"+id).then(res =>{
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
      this.request.post("/files/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },

    changeEnable(row){
      this.request.post("/files/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("操作成功")
        }
      })

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
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleFileUploadSuccess(res) {
      console.log(res)
      this.$message.success("上传成功")
      this.load()
    },
    download(url) {
      window.open(url)
    },
    preview(url) {
      //有点问题，还没实现
      window.open('https://file.keking.cn/onlinePreview?url=' + encodeURIComponent(window.btoa((url))))
    },
  },


}
</script>

<style scoped>

</style>
