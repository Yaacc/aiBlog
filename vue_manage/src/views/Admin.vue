<template>
  <div>
    <div class="button-left">
      <el-button type="success" icon="el-icon-edit" @click="dialogFormVisible=true">新增</el-button>
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

      <!--        :action="'http://'+localhost+'/user/import'"-->
      <el-upload action="http://localhost/admin/import"
                 :show-file-list="false" accept="xlsx"
                 :on-success="handleExcelImportSuccess"
                 style="display: inline-block"
                 class="ml10px"
                 limit="1"
      >
        <el-button type="primary" plain>导入<i class="el-icon-upload el-icon--right"></i></el-button>
      </el-upload>

      <el-button type="primary" @click="exp" plain>导出<i class="el-icon-download el-icon--right"></i></el-button>

    </div>
    <div class="search-input">
      <div class="input-suffix">
        <el-input
          placeholder="输入用户名"
          v-model="searchUserName"
          clearable
          style="width: 180px">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
        <el-input
          placeholder="输入真实姓名"
          v-model="searchRealName"
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
      <el-table-column prop="adminNumber" label="管理员编号" width="120"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80"></el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column prop="idcard" label="身份证号"></el-table-column>
      <el-table-column prop="action" label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            plain
            @click="handleEdit(scope.$index, scope.row)">编辑
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
    <!--    对话框-->
    <div>
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%" center>
        <el-form :model="form">
          <el-form-item label="管理员编号" :label-width="formLabelWidth">
            <el-input v-model="form.adminNumber" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="用户名" :label-width="formLabelWidth">
            <el-input v-model="form.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="真实姓名" :label-width="formLabelWidth">
            <el-input v-model="form.realName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="formLabelWidth">
            <el-input v-model="form.sex" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="年龄" :label-width="formLabelWidth">
            <el-input v-model="form.age" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" :label-width="formLabelWidth">
            <el-input v-model="form.idcard" autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--    dialog-->
  </div>
  <!--  main-->
</template>

<script>
export default {
  name: "Admin.vue",
  data() {
    return {
      tableData: [],
      total: 0,
      currentPage: 1,
      pageNum: 1,
      pageSize: 5,
      searchRealName: '', // 搜索框
      searchUserName: '',
      dialogFormVisible: false,
      isLoading: false,
      multipleSelection: [],
      form: {
        adminNumber: '',
        username: '',
        realName: '',
        sex: '',
        age: '',
        idcard: ''
      },
      formLabelWidth: '80px'
    }
  },
  created() {
    /*fetch("http://localhost:80/admin/page").then(res => res.json()).then(res =>{
      console.log(res);
      this.tableData=res.data()
    })*/
    this.load()
  },
  methods: {
    load() {
      this.request.get("/admin/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.searchUserName,
          realName: this.searchRealName
        }
      }).then(res => {
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.searchUserName = ""
      this.searchRealName = ""
      this.load()
    },
    // 新增
    save(){
      this.request.post("/admin",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    // 按序号删除
    del(id){
      this.request.delete("/admin/"+id).then(res =>{
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
      this.request.post("/admin/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    // 导出
    exp(){
      window.open('http://localhost/admin/export')
    },
    // 表格导入提醒
    handleExcelImportSuccess(){
      this.$message.success("导入成功")
      this.load()
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
