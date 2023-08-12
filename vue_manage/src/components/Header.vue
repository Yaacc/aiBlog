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
      <span style="margin-right: 12px; font-size: 16px;">{{ admin.username }}</span>
      <el-dropdown>
        <i class="el-icon-arrow-down"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="personal()">个人信息</el-dropdown-item>
          <el-dropdown-item @click.native="changePassword()">修改密码</el-dropdown-item>
          <el-dropdown-item @click.native="logout()">退出</el-dropdown-item>
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
  },
  data(){
    return{
      admin:localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")):{}
    }
  },
  methods: {
    logout() {
      localStorage.clear();
      this.$router.push("/");
    },
    personal(){
      this.$router.push("Personal");
    },
    changePassword(){
      this.$router.push("ChangePassword");
    }
  },
}
</script>

<style scoped>
.header-userInfo{
  float: right;
}
</style>
