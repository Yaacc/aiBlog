<template>
  <div class="wrapper">
    <div style="margin: 100px auto; background-color: #fff; width: 400px; height: 500px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px"><b>注 册</b></div>
      <el-form :model="admin" :rules="rules" ref="adminForm">
        <el-form-item prop="userNumber">
          <el-input placeholder="请输入账号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="admin.adminNumber"></el-input>
        </el-form-item>
        <el-form-item prop="realName">
          <el-input placeholder="请输入名字" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="admin.realName"></el-input>
        </el-form-item>
        <el-form-item prop="idcard">
          <el-input placeholder="请输入身份证号" size="medium" style="margin: 5px 0" prefix-icon="el-icon-user" v-model="admin.idcard"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="admin.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 5px 0" prefix-icon="el-icon-lock" show-password v-model="admin.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item style="margin: 5px 0; text-align: right">
          <el-button type="primary" size="small"  autocomplete="off" @click="login">注册</el-button>
          <el-button type="warning" size="small"  autocomplete="off" @click="$router.push('/')">返回登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      admin: {},
      rules: {
        adminNumber:[
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 5, max: 10, message: '长度在 5 到 10 个字符', trigger: 'blur' }
        ],
        idcard:[
          { required: true, message: '请输入身份正号', trigger: 'blur' },
          { min: 18, max: 18, message: '长度在 18 个字符', trigger: 'blur' }
        ],
        realName: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 5, message: '长度在 2 到 5 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['adminForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.admin.password !== this.admin.confirmPassword) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/admin", this.admin).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功")
              this.$router.push('/')
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background: gray;
  overflow: hidden;
}
</style>
