<template>
  <el-card style="width: 600px; margin: 0 auto">
    <h1>Personal Information</h1>
    <h2>个人信息</h2>
    <el-form label-width="100px">
      <el-upload
        class="avatar-uploader"
        :action="'http://localhost/files/upload'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="管理员编号">
        <el-input v-model="form.userNumber" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="用户名">
        <el-input v-model="form.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="真实姓名">
        <el-input v-model="form.realName" disabled autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-input v-model="form.sex" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input v-model="form.age" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="电话号码">
        <el-input v-model="form.phone" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="身份证号码">
        <el-input v-model="form.idcard" autocomplete="off"></el-input>
      </el-form-item>
<!--      <el-form-item label="地址">-->
<!--        <el-input type="textarea" v-model="form.address" autocomplete="off"></el-input>-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="$router.push('/manage') " >返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Personal",
  data() {
    return {
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  created() {
    this.getAdmin().then(res => {
      console.log(res)
      this.form = res
    })
  },
  methods: {
    async getAdmin() {
      // 通过用户名
      // return (await this.request.get("/admin/username/" + this.admin.username)).data
      return (await  this.request.get("/user/"+ this.user.id)).data
    },
    save() {
      this.request.post("/user/update", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")

          // 触发父级更新User的方法
          this.$emit("refreshAdmin")

          // 更新浏览器存储的用户信息
          this.getAdmin().then(res => {
            res.token = JSON.parse(localStorage.getItem("user")).token
            localStorage.setItem("user", JSON.stringify(res))
          })

        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    }
  }
}
</script>

<style>
.avatar-uploader {
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>
