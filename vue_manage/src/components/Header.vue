/**
* 头部
*/
<template>
  <div>
    <div style="float: left; font-size: 24px;">
      <span
        :class="collapseBtnClass"
        style="cursor: pointer"
        @click="collapse"
      ></span>
    </div>
    <div class="header-userInfo">
      <el-avatar style="vertical-align: middle;margin-right: 8px;"
                 src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>
      <span style="margin-right: 12px; font-size: 16px;">{{ adminName }}</span>
      <el-dropdown>
        <i class="el-icon-arrow-down"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人信息</el-dropdown-item>
          <el-dropdown-item>退出</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>

</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
    adminName: String,
  },
}
</script>
<style scoped>

.header-userInfo{
  float: right;
}
</style>
