<template>
<!--  <div>-->
<!--    <div class="button-left">-->
<!--      <el-button type="success" icon="el-icon-edit" @click="dialogFormVisible=true">新增</el-button>-->
<!--      <el-button type="danger" icon="el-icon-delete" plain>批量删除</el-button>-->
<!--      <el-button type="primary" plain>下载<i class="el-icon-download el-icon&#45;&#45;right"></i></el-button>-->
<!--      <el-button type="primary" plain>上传<i class="el-icon-upload el-icon&#45;&#45;right"></i></el-button>-->
<!--    </div>-->
<!--    <div class="search-input">-->
<!--      <div class="input-suffix">-->
<!--        <el-input-->
<!--          placeholder="输入用户名"-->
<!--          v-model="searchUserName"-->
<!--          clearable-->
<!--          style="width: 180px">-->
<!--          <i slot="prefix" class="el-input__icon el-icon-search"></i>-->
<!--        </el-input>-->
<!--        <el-input-->
<!--          placeholder="输入真实姓名"-->
<!--          v-model="searchRealName"-->
<!--          clearable-->
<!--          style="width: 180px">-->
<!--          <i slot="prefix" class="el-input__icon el-icon-search"></i>-->
<!--        </el-input>-->
<!--      </div>-->
<!--      <el-button :loading="isLoading" @click="load" type="primary" icon="el-icon-search">搜索</el-button>-->
<!--      <el-button @click="reset">重置</el-button>-->
<!--    </div>-->
<!--    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">-->
<!--      <el-table-column type="selection" width="55"></el-table-column>-->
<!--      <el-table-column prop="id" label="ID" width="60"></el-table-column>-->
<!--      <el-table-column prop="userNumber" label="用户编号" width="120"></el-table-column>-->
<!--      <el-table-column prop="username" label="用户名" width="120"></el-table-column>-->
<!--      <el-table-column prop="realName" label="真实姓名" width="120"></el-table-column>-->
<!--      <el-table-column prop="sex" label="性别" width="80"></el-table-column>-->
<!--      <el-table-column prop="age" label="年龄" width="80"></el-table-column>-->
<!--      <el-table-column prop="phone" label="联系电话" width="160"></el-table-column>-->
<!--      <el-table-column prop="idcard" label="身份证号"></el-table-column>-->
<!--      <el-table-column prop="action" label="操作" width="200" fixed="right">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="danger"-->
<!--            plain-->
<!--            @click="handleDelete(scope.$index, scope.row)">删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--    </el-table>-->
<!--    <div class="block">-->
<!--      <el-pagination-->
<!--        background-->
<!--        @size-change="handleSizeChange"-->
<!--        @current-change="handleCurrentChange"-->
<!--        :current-page.sync="currentPage"-->
<!--        :page-sizes="[1,5,10,15]"-->
<!--        :page-size="pageSize"-->
<!--        layout="total, sizes, prev, pager, next, jumper"-->
<!--        :total="total">-->
<!--      </el-pagination>-->
<!--    </div>-->
</template>

<script>
export default {
  name: "Files"
}
</script>

<style scoped>

</style>
