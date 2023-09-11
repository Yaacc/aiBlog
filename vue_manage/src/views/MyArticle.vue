<template>
  <div>
    <div class="button-left">
      <el-button type="success" icon="el-icon-edit" @click="clc(),dialogFormVisible=true">发布</el-button>
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
          <el-button
            size="mini"
            type="success"
            plain
            @click="assignment(scope.row),dialogFormVisible=true,temp=true">编辑
          </el-button>
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
          <el-form-item label="作者">
            <el-input  v-model="form.user" disabled autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="文章内容" >
            <el-input type="textarea" autosize v-model="form.content"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button v-if="temp" type="primary" @click="changeEnable(form)">确 定</el-button>
          <el-button v-if="!temp" type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>
    </div>

    <div>
      <el-dialog title="查看文章" :visible.sync="dialogFormVisibles" width="40%" center>
        <el-form :model="forms">
          <el-form-item>
            <h2 style="text-align: center">{{forms.name}}</h2>
            <!--            <el-input v-model="forms.name" readonly="true" autocomplete="off" ></el-input>-->
          </el-form-item>

          <h4 style="text-align: center"><span style="font-weight: bold">作者:</span>{{forms.user}}</h4>
          <!--            <el-input  v-model="forms.user" readonly="true" autocomplete="off"></el-input>-->
          <el-form-item>
            <el-input  type="textarea" autosize v-model="forms.content" readonly="true" autocomplete="off">
            </el-input>
          </el-form-item>
        </el-form>
        <div id = "main">
          <i style="color: orange" :class="['iconfont',favorite ? 'icon-star__easyico' : 'icon-shoucang']" @click="FavoriteArticle(forms)"><span class="fonts">{{ forms.collect }}</span></i>
          <i :class="['iconfont',liked ? 'icon-dianzan' : 'icon-xihuan']" @click="LikeArticle(forms)"> <span class="fonts">{{ forms.likes }}</span></i>
        </div>

        <el-card style="margin-top: 60px">
          <div style="padding: 20px; color: #888">
            <div>
              <el-input type="textarea" :rows="1" v-model="entity.content"></el-input>
              <div style="text-align: right; padding: 10px"><el-button type="primary" @click="saveComment(forms.id)">评论</el-button></div>
            </div>
          </div>
          <div style="display: flex; padding: 20px" v-for="item in comments">
            <div style="text-align: center; flex: 1">
              <el-image :src="item.avatar" style="width: 60px; height: 60px; border-radius: 50%"></el-image>
            </div>
            <div style="padding: 0 10px; flex: 5">
              <div><b style="font-size: 14px">{{ item.userNumber }}</b></div>
              <div style="padding: 10px 0; color: #888">
                {{ item.content }}
                <el-button type="text" size="mini" @click="delComment(item)" v-if="item.userId === user.id">删除</el-button>
              </div>
              <div style="background-color: #eee; padding: 10px" v-if="item.parentComment">{{ item.userNumber }}：{{ item.parentComment.content }}</div>
              <div style="color: #888; font-size: 12px">
                <span>{{ item.createTime}}</span>
                <el-button type="text" style="margin-left: 20px" @click="reReply(item.id)">回复</el-button>
              </div>
            </div>
          </div>
<!--          <div style="display: flex; padding: 20px" v-for="sub in comments">
            <div style="background-color: #eee; padding: 10px" v-if="item.parentComment">{{ item.userNumber }}：{{ item.parentComment.content }}</div>
          </div>-->
        </el-card>
        <el-dialog title="回复信息" :visible.sync="dialogFormVisible1" width="30%">
          <el-form :model="entity" label-width="80px">
            <el-form-item label="内容">
              <el-input v-model="entity.reply" autocomplete="off" type="textarea" :rows="3"></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="reply(forms.id)">确 定</el-button>
          </template>
        </el-dialog>


        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisibles = false">返回</el-button>
<!--          <el-button v-if="temp" type="primary" @click="changeEnable(forms)">确 定</el-button>-->
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import admin from "./Admin";
import request from "@/utils/request";
export default {
  name: "Article",
  data(){
    return{
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      currentPage: 1,
      dialogFormVisible: false,
      dialogFormVisible1: false,
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
        collect :0,
      },
      forms: {
        id:0,
        name: '',
        content: '',
        user:'',
        likes:0,
        collect: 0,
      },
      formLabelWidth: '80px',
      liked:false,
      favorite:false,
      temp:false,

      comments:[],
      entity: {}
    }
  },
  created() {
    this.form.user=this.user.userNumber
    this.load()
  },
  methods:{
    load(){
      this.request.get("/article/myArticle/page",{
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
    //判断是否已点赞
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
    //点赞
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
    //判断是否已收藏
    isFavorite(id){
      this.request.get("/article/isFavorite/"+id).then(res=>{
        if(res.code==='200'){
          this.favorite = true;
        }else {
          this.favorite = false;
        }
        this.load()
      })
    },
    FavoriteArticle(row){
      if(this.favorite===false){
        this.request.post("/article/favorite/"+row.id,row).then(res=>{
          if(res.code==='200'){
            this.$message.success("收藏成功")
            this.load()
            row.collect=row.collect+1
            this.preview(row)
          }else{
            this.$message.error("收藏失败")
          }
        })
      }
      else {
        this.request.delete("/article/favorite/"+row.id,row).then(res=>{
          if(res.code==='200'){
            this.$message.success("取消收藏")
            this.load()
            row.collect=row.collect-1
            this.preview(row)
          }else{
            this.$message.error("收藏取消失败")
          }
        })
      }
    },
    changeEnable(row){
      this.request.post("/article/update",row).then(res=>{
        if(res.code==='200'){
          this.$message.success("修改成功")
          this.dialogFormVisible = false
          this.load()
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
    assignment(article){
      this.form.user=article.user
      this.form.name=article.name
      this.form.content=article.content
      this.form.id=article.id;
    },
    clc(){
      this.form.name=''
      this.form.content=''
    },
    preview(article){
      this.forms.user=article.user
      this.forms.name=article.name
      this.forms.content=article.content
      this.forms.id=article.id;
      this.forms.likes=article.likes
      this.forms.collect=article.collect
      this.isLike(article.id)
      this.isFavorite(article.id)
      this.loadMessage(article.id)
      this.dialogFormVisibles=true
      //this.preview(article)
      this.load()
    },

    saveComment(id){
      // 如果是评论的话，在 save的时候要注意设置 当前模块的id为 foreignId。也就是  entity.foreignId = 模块id
      if (!this.user.username) {
        this.$message({
          message: "请登录",
          type: "warning"
        });
        return;
      }
      if (!this.entity.content) {
        this.$message({
          message: "请填写内容",
          type: "warning"
        });
        return;
      }
      this.entity.articleId=id
      request.post("/comment/article", this.entity).then(res => {
        if (res.code === '200') {
          this.$message({
            message: "评论成功",
            type: "success"
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
        this.entity = {}
        this.loadMessage(id);
        this.dialogFormVisible = false;
      })
    },
    loadMessage(id) {
      // 如果是留言的话，就写死=0
      // 如果是 评论，则需要设置 当前被评论的模块的id作为foreignId
      //let foreignId = 0;
      request.get("/comment/article/"+id).then(res => {
        this.comments = res.data;
      })
    },
    delComment(row){
      request.delete("/comment/" +row.id).then(res => {
        this.$message({
          message: "删除成功",
          type: "success"
        });
        this.loadMessage(row.articleId)
      })
    },
    reReply(id) {
      this.dialogFormVisible1 = true;
      this.entity.parentId = id;
    },
    reply(id) {
      this.entity.content = this.entity.reply;
      this.saveComment(id)
      this.loadMessage(id)
    },
    cancel() {
      this.dialogFormVisible1 = false;
      this.entity = {};
    },

  },
}
</script>

<style scoped>
#main i{
  float: right;
  margin: 5px 20px;
  font-size: 25px; /* 控制图标的大小 */
  color: #ff0000; /*控制图标的颜色*/
}
.fonts{
  font-size: 15px;
  margin-left: 5px;
  font-weight: lighter;
}
</style>
