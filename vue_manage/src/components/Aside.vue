/**
* 侧边栏
*/
<template>
  <el-menu
    :default-openeds="['2']"
    class="el-menu-vertical-demo"
    :collapse="isCollapse"
    :collapse-transition="false"
    active-text-color="orange"
    text-color="#fff"
    router
  >
    <div class="menuHeader">
      <img src="../assets/logo.png" alt="">
      <span v-show="menuHeader">后台管理系统</span>
    </div>
    <el-menu-item index="/">
      <i class="el-icon-house"></i>
      <span slot="title">主页</span>
    </el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-menu"></i>
        <span slot="title">系统管理</span>
      </template>
      <el-menu-item-group>
        <template slot="title">分组一</template>
        <el-menu-item index="/user">职工管理</el-menu-item>
        <el-menu-item index="/admin">管理员</el-menu-item>
      </el-menu-item-group>
        <el-menu-item index="">设置</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    menuHeader: Boolean
  }
}
</script>

<style scoped>
.menuHeader{
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: #fff;
  font-size: 20px;
}
.menuHeader span{
  margin-left: 10px;
}
.menuHeader img{
  width: 25px;
}

</style>
