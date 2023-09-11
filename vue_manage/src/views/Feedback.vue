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
          placeholder="输入反馈关键词"
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
      <el-table-column prop="userNumber" label="反馈者" align="center"></el-table-column>
      <el-table-column prop="content" label="反馈内容" align="center"></el-table-column>
      <el-table-column prop="createDate" label="反馈时间" align="center"></el-table-column>
      <el-table-column prop="answer" label="回复内容" align="center"></el-table-column>
      <el-table-column prop="answerDate" label="回复时间" align="center"></el-table-column>
      <el-table-column prop="action" label="操作" fixed="right" align="center">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            plain
            @click="reReply(scope.row)">回复
          </el-button>
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
    <el-dialog title="回复信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form :model="form" label-width="80px">
        <el-form-item label="内容">
          <el-input v-model="form.answer" autocomplete="off" type="textarea" :rows="5"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="changeEnable(form)">确 定</el-button>
      </template>
    </el-dialog>
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
  name: "Feedback",
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
        content: '',
        answer:'',
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/feedback/page",{
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
    // 按序号删除
    del(id){
      this.request.delete("/feedback/"+id).then(res =>{
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
      this.request.post("/feedback/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    changeEnable(row) {
      if (!this.form.answer) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      this.request.post("/feedback/update", row).then(res => {
        if (res.code === '200') {
          this.$message({
            message: "回复成功",
            type: "success"
          });
        } else {
          this.$message({
            message: "回复失败",
            type: "error"
          });
        }
        this.dialogFormVisible = false;
        this.load();
      })
    },
    reReply(row) {
      this.form.id=row.id
      this.form.content=row.content
      this.dialogFormVisible = true;
    },
    cancel() {
      this.dialogFormVisible = false;
      this.form = {};
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
