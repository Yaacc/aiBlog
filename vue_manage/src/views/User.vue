<template>
  <div>
    <div class="button-left">
      <el-button type="success" icon="el-icon-edit" @click="dialogFormVisible=true">新增</el-button>
      <el-button type="danger" icon="el-icon-delete" plain>批量删除</el-button>
      <el-button type="primary" plain>下载<i class="el-icon-download el-icon--right"></i></el-button>
      <el-button type="primary" plain>上传<i class="el-icon-upload el-icon--right"></i></el-button>
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
    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="60"></el-table-column>
      <el-table-column prop="userNumber" label="用户编号" width="120"></el-table-column>
      <el-table-column prop="username" label="用户名" width="120"></el-table-column>
      <el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>
      <el-table-column prop="sex" label="性别" width="80"></el-table-column>
      <el-table-column prop="age" label="年龄" width="80"></el-table-column>
      <el-table-column prop="phone" label="联系电话" width="160"></el-table-column>
      <el-table-column prop="idcard" label="身份证号"></el-table-column>
      <!--      <el-table-column prop="email" label="邮箱" width="210"></el-table-column>-->
      <!--      <el-table-column prop="address" label="地址"></el-table-column>-->
      <el-table-column prop="action" label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            plain
            @click="handleEdit(scope.$index, scope.row)">编辑
          </el-button>
          <el-button
            size="mini"
            type="danger"
            plain
            @click="handleDelete(scope.$index, scope.row)">删除
          </el-button>
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
    <!--    对话框-->
    <div>
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="40%" center>
        <el-form :model="form">
          <el-form-item label="用户编号" :label-width="formLabelWidth">
            <el-input v-model="form.userNumber" autocomplete="off"></el-input>
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
          <el-form-item label="联系电话" :label-width="formLabelWidth">
            <el-input v-model="form.phone" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="身份证号" :label-width="formLabelWidth">
            <el-input v-model="form.idcard" autocomplete="off"></el-input>
          </el-form-item>
          <!--          <el-form-item label="邮箱" :label-width="formLabelWidth">-->
          <!--            <el-input v-model="form.email" autocomplete="off"></el-input>-->
          <!--          </el-form-item>-->
          <!--          <el-form-item label="地址" :label-width="formLabelWidth">-->
          <!--            <el-input v-model="form.address" autocomplete="off"></el-input>-->
          <!--          </el-form-item>-->
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    <!--    dialog-->
  </div>
  <!--  main-->
</template>

<script>
export default {
  name: "User",
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
        userNumber: '',
        username: '',
        realName: '',
        sex: '',
        age: '',
        phone: '',
        idcard: ''
        // email: '',
        // address: ''
      },
      formLabelWidth: '80px'
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/user/page", {
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
      // fetch("http://localhost/user/page?pageNum="+this.pageNum+"&pageSize="+this.pageSize).then(res=>res.json()).then(res=>{
      //   console.log(res)
      //   this.tableData = res.data
      //   this.total = res.total
      // })
    },
    reset() {
      this.searchUserName = ""
      this.searchRealName = ""  //清空搜索框
      this.load()         //刷新页面
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    handleEdit(index, row) {
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
