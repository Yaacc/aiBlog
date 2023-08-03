<template>
  <div class="wrapper">
    <div
      style="margin:200px auto; background-color: rgba(255,255,255,0.7); width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align:center;font-size: 24px">登录</div>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
      <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" v-model="user.password" show-password></el-input>
      <div style="margin: 10px 0; text-align: right">
        <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
        <el-button type="warning" size="small" autocomplete="off">注册</el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login.vue",
  data() {
    return {
      user: {}
    }
  },
  methods: {
    login() {

    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, #fc466b, #3f5efb);
  overflow: hidden;
}
</style>
