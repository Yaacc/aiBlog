<template>
  <el-card style="width: 700px; margin: 0 auto">
    <h1>Edit Video</h1>
    <h2>视频编辑</h2>
    <el-form label-width="100px" style="margin-top: 50px">
<!--      <el-upload
        class="avatar-uploader"
        :action="'http://localhost/files/upload'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>-->
      <el-form-item label="视频标题">
        <el-input v-model="form.videoTitle" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="视频简介">
        <el-input v-model="form.videoDesc" :rows="5" type="textarea" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="上传视频">
      <el-upload
        :action="'http://localhost/video/upload'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
      >
<!--        <img v-if="form.videoCover" :src="form.videoCover" class="avatar">-->
        <video v-if="form.videoPath" :src="form.videoPath" class="avatar" controls="controls"></video>
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        <div slot="tip" class="el-upload__tip">支持扩展名：.mp4</div>
      </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="save">确 定</el-button>
        <el-button @click="$router.push('/manage') " >返回</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "VideoPublish",
  data() {
    return {
      form: {
        id:0,
        videoTitle:'',
        videoCover:'',
        videoDesc:'',
        videoPath:'',
      },
    }
  },
  created() {
  },
  methods: {
    save() {
      this.request.post("/video/public", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    handleAvatarSuccess(res) {
      this.form.videoPath=res.data.videoPath,
        this.form.videoCover=res.data.videoCover
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
