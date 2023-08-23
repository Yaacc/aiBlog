<template>
  <div>
    <div class="button-left">
      <el-button type="success" icon="el-icon-edit" @click="dialogFormVisible=true">发布</el-button>
<!--      <el-upload action="http://localhost/files/upload"-->
<!--                 :show-file-list="false"-->
<!--                 :on-success="handleFileUploadSuccess"-->
<!--                 style="display: inline-block">-->
<!--        <el-button type="primary" class="ml10px" icon="el-icon-upload el-icon&#45;&#45;right" plain>上传文件</el-button>-->
<!--      </el-upload>-->
      <el-popconfirm
        class="ml10px"
        confirm-button-text='确定'
        cancel-button-text='取消'
        icon="el-icon-info"
        icon-color="red"
        title="您确定批量删除这些数据吗？"
        @confirm="delBatch"
      >
        <el-button type="danger" icon="el-icon-delete" slot="reference" plain>批量删除</el-button>
      </el-popconfirm>

    </div>
    <div class="search-input">
      <div class="input-suffix">
        <el-input
          placeholder="输入文章名称"
          v-model="searchArticleName"
          clearable
          style="width: 180px">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <el-button :loading="isLoading" @click="load" type="primary" icon="el-icon-search">搜索</el-button>
      <el-button @click="reset">重置</el-button>
    </div>

    <el-table :data="tableData" stripe @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
      <el-table-column prop="user" label="作者" width="80" align="center"></el-table-column>
      <el-table-column prop="name" label="文章名称" align="center"></el-table-column>
      <el-table-column prop="likes" label="点赞数" align="center"></el-table-column>
      <el-table-column prop="collect" label="收藏数" align="center"></el-table-column>
<!--      <el-table-column label="预览" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="primary"-->
<!--                     size="mini"-->
<!--                     plain-->
<!--                     @click="preview(scope.row.url)">预览</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
<!--      <el-table-column label="下载" align="center">-->
<!--        <template slot-scope="scope">-->
<!--          <el-button type="primary"-->
<!--                     size="mini"-->
<!--                     plain-->
<!--                     @click="download(scope.row.url)">下载</el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column label="预览" align="center">
        <template slot-scope="scope">
          <el-button type="primary"
                     size="mini"
                     plain
                     @click="preview(scope.row)">预览</el-button>
        </template>
      </el-table-column>
      <el-table-column label="启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enable"
                     active-color="#13ce66"
                     inactive-color="#ccc"
                     @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-popconfirm
            class="ml10px"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗？"
            @confirm="del(scope.row.id)"
          >
            <el-button type="danger"
                       size="mini"
                       plain
                       slot="reference">删除<i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-sizes="[1,5,10,15]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <!--    对话框-->
    <div>
      <el-dialog title="发表文章" :visible.sync="dialogFormVisible" width="40%" center>
        <el-form :model="form">
          <el-form-item label="标题" >
            <el-input v-model="form.name" autocomplete="off"></el-input>
          </el-form-item>
<!--          <el-form-item label="内容" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off"></el-input>
          </el-form-item>-->
          <el-form-item label="作者">
            <el-input  v-model="form.user" disabled autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文章内容" >
            <el-input type="textarea" v-model="form.content"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog title="查看文章" :visible.sync="dialogFormVisibles" width="40%" center>
        <el-form :model="forms">
          <el-form-item label="标题" >
            <el-input v-model="forms.name" disabled autocomplete="off" ></el-input>
          </el-form-item>
          <!--          <el-form-item label="内容" :label-width="formLabelWidth">
                      <el-input v-model="form.content" autocomplete="off"></el-input>
                    </el-form-item>-->
          <el-form-item label="作者">
            <el-input  v-model="forms.user" disabled autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文章内容" >
            <el-input type="textarea" v-model="forms.content" disabled autocomplete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibles = false">返回</el-button>
<!--          <el-button type="primary" @click="save">确 定</el-button>-->
        </div>
        <div>
            <button @click="LikeArticle(forms)" :class="{ liked: liked }">
              {{ liked ? 'Unlike' : 'Like' }}
            </button>
            <span>{{ forms.likes }}</span>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import admin from "./Admin";

export default {
  name: "Article",
  data(){
    return{
      admin: localStorage.getItem("admin") ? JSON.parse(localStorage.getItem("admin")) : {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      currentPage: 1,
      dialogFormVisible: false,
      dialogFormVisibles: false,
      searchArticleName: '', // 搜索框
      isLoading:false,
      visible:true,
      form: {
        id:0,
        name: '',
        content: '',
        user:'',
        likes:0,
      },
      forms: {
        id:0,
        name: '',
        content: '',
        user:'',
        likes:0,
      },
      formLabelWidth: '80px',
      liked:false,
    }
  },
  created() {
    this.form.user=this.admin.adminNumber
    this.load()
  },
  methods:{
    load(){
      this.request.get("/article/page",{
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.searchArticleName,
        }

      }).then(res=>{
        console.log(res)
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },
    reset() {
      this.searchArticleName = ""
      this.load()
    },
    //是否已点赞
    isLike(id){
      this.request.get("/article/isLike/"+id).then(res=>{
        if(res.code==='200'){
          this.liked = true;
        }else {
          this.liked = false;
        }
        this.load()
      })
    },
    // 按序号删除
    del(id){
      this.request.delete("/article/"+id).then(res =>{
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.load()
        }else{
          this.$message.error("删除失败")
        }
      })
    },
    // 批量删除
    delBatch(){
      let ids = this.multipleSelection.map(value => value.id)
      this.request.post("/article/del/batch",ids).then(res =>{
        if(res.code==='200'){
          this.$message.success("批量删除成功")
          this.load()
        }else{
          this.$message.error("批量删除失败")
        }
      })
    },
    // 新增
    save(){
      this.request.post("/article/publish",this.form).then(res=>{
        if(res.code === '200'){
          this.$message.success("发布成功")
          this.dialogFormVisible = false
          this.reset();
        }else{
          this.$message.error("保存失败")
        }
      })
    },
    LikeArticle(row){
      if(this.liked===false){
        this.request.post("/article/likes/"+row.id,row).then(res=>{
          if(res.code==='200'){
            this.$message.success("点赞成功")
            this.load()
            row.likes=row.likes+1
            this.preview(row)
          }else{
            this.$message.error("点赞失败")
          }
        })
      }
      else {
        this.request.delete("/article/likes/"+row.id,row).then(res=>{
          if(res.code==='200'){
            this.$message.success("取消点赞")
            this.load()
            row.likes=row.likes-1
            this.preview(row)
          }else{
            this.$message.error("点赞取消失败")
          }
        })
      }

    },
    changeEnable(row){
      this.request.post("/article/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("操作成功")
        }
      })
    },
    handleSizeChange(pageSize) {
      console.log(`每页 ${pageSize} 条`)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(`当前页: ${pageNum}`)
      this.pageNum = pageNum
      this.load()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // handleFileUploadSuccess(res) {
    //   console.log(res)
    //   this.$message.success("上传成功")
    //   this.load()
    // },

    preview(article){
      this.forms.user=article.user
      this.forms.name=article.name
      this.forms.content=article.content
      this.forms.id=article.id;
      this.forms.likes=article.likes
      this.isLike(article.id)
      this.dialogFormVisibles=true
      //this.preview(article)
      this.load()
    }
  },
}
</script>

<style scoped>
.liked {
  color: red;
  font-weight: bold;
}
</style>
