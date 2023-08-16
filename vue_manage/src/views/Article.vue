<template>
  <div>
    <div class="button-left">
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
          placeholder="输入文章标题"
          v-model="searchName"
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
      <el-table-column prop="user" label="发布人编号"></el-table-column>
      <el-table-column prop="name" label="标题" ></el-table-column>
      <el-table-column prop="time" label="发布时间" ></el-table-column>
      <el-table-column prop="likes" label="点赞数" ></el-table-column>
      <el-table-column prop="collect" label="收藏数" ></el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable"
                     active-color="#13ce66"
                     inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="action" label="操作"  fixed="right">
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
    <!--    分页栏-->
    <!--    dialog-->
  </div>
  <!--  main-->
</template>

<script>
export default {
  name: "Article.vue",
  data() {
    return {
      tableData: [],
      total: 0,
      currentPage: 1,
      pageNum: 1,
      pageSize: 5,
      searchName: '', // 搜索框
      multipleSelection: [],
      isLoading: false,
      formLabelWidth: '80px'
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/article/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.searchName,
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.searchName = ""
      this.load()
    },
    // 按序号删除
    del(id){
      this.request.delete("/article/"+id).then(res =>{
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
      this.request.post("/article/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {
      this.form=row;
      this.dialogFormVisible=true;
      console.log(index, row);
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
