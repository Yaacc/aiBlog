<template>
  <div class="wrapper">
    <div
      style="margin:200px auto; background-color: rgba(255,255,255,0.7); width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align:center;font-size: 24px">登录</div>
      <el-form :model="loginForm" :rules="rules">
        <el-form-item prop="userNumber">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="loginForm.userNumber"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" v-model="loginForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item style="margin: 10px 0; text-align: right">
          <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
          <el-button type="warning" size="small" autocomplete="off">注册</el-button>
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
      loginForm: {
        userNumber: '',
        password:''
      },
      rules: {
        userNumber:[
          {required: true,message:'输入用户账号',trigger:'blur'},
          {min:2,max:10,message:'长度在2到10个字符',trigger: 'blur'}
        ],
        password:[
          {required:true,message: '输入密码',trigger: 'blur'},
          {min:3,max:10,message:'长度在3到10个字符',trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    // submitForm(formName) {
    //   this.$refs[formName].validate((valid) => {
    //     if (valid) {
    //       // alert('submit!');
    //       this.login()
    //     } else {
    //       console.log('error submit!!');
    //       return false;
    //     }
    //   });
    // },
    login() {
      this.request.post("user/login",this.loginForm).then(res=>{
        if(res.code === "200"){
          this.$router.push("/")
        }else{
          this.$message.error("错误")
        }
      })
    }
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-color: gray;
  overflow: hidden;
}
</style>
