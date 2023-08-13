<template>
  <el-card style="width: 600px; margin: 0 auto">
    <h1>修改密码</h1>
    <el-form label-width="100px" :model="form" :rules="rules" ref="pass">
      <el-form-item label="管理员编号">
        <el-input v-model="form.adminNumber" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="旧密码" prop="password">
        <el-input v-model="form.password" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="新密码" prop="newPassword">
        <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item label="确认新密码" prop="confirmPassword">
        <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>

</template>

<script>
export default {
  name: "ChangePassword",
  data() {
    return {
      form: {},
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
      rules: {
        password: [
          { required: true, message: '请输入原密码', trigger: 'blur' },
          { min: 3, message: '长度不少于3位', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 3, message: '长度不少于3位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, message: '长度不少于3位', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.form.adminNumber = this.admin.adminNumber
  },
  methods: {
    save() {
      this.$refs.pass.validate((valid) => {
        if (valid) {
          if (this.form.newPassword !== this.form.confirmPassword) {
            this.$message.error("2次输入的新密码不相同")
            return false
          }
          this.request.post("/admin/password", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success(res.msg)
              this.$router.push("/")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
