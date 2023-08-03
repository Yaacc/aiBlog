<template>
  <el-container style="min-height: 100vh;">
    <el-aside :width="sideWith+'px'">
      <Aside :isCollapse="isCollapse" :menuHeader="menuHeader"/>
      <!--      <el-menu-->
      <!--        :default-openeds="['1']"-->
      <!--        class="el-menu-vertical-demo"-->
      <!--        :collapse="isCollapse"-->
      <!--        :collapse-transition="false"-->
      <!--        active-text-color="orange"-->
      <!--        text-color="#fff"-->
      <!--      >-->
      <!--        <div class="menuHeader">-->
      <!--          <img src="../assets/logo.png" alt="">-->
      <!--          <span v-show="menuHeader">后台管理系统</span>-->
      <!--        </div>-->
      <!--        <el-submenu index="1">-->
      <!--          <template slot="title">-->
      <!--            <i class="el-icon-message"></i>-->
      <!--            <span slot="title">导航一</span>-->
      <!--          </template>-->
      <!--        </el-submenu>-->
      <!--        <el-submenu index="2">-->
      <!--          <template slot="title">-->
      <!--            <i class="el-icon-menu"></i>-->
      <!--            <span slot="title">导航二</span>-->
      <!--          </template>-->
      <!--          <el-menu-item-group>-->
      <!--            <template slot="title">分组一</template>-->
      <!--            <el-menu-item index="2-1">选项1</el-menu-item>-->
      <!--            <el-menu-item index="2-2">选项2</el-menu-item>-->
      <!--          </el-menu-item-group>-->
      <!--          <el-menu-item-group title="分组2">-->
      <!--            <el-menu-item index="2-3">选项3</el-menu-item>-->
      <!--          </el-menu-item-group>-->
      <!--          <el-submenu index="2-4">-->
      <!--            <template slot="title">选项4</template>-->
      <!--            <el-menu-item index="2-4-1">选项4-1</el-menu-item>-->
      <!--          </el-submenu>-->
      <!--        </el-submenu>-->
      <!--      </el-menu>-->
    </el-aside>

    <el-container>
      <el-header>
        <Header :collapse="collapse" :collapseBtnClass="collapseBtnClass" :admin-name="adminName"/>
        <!--        <div style="float: left; font-size: 24px;">-->
        <!--          <span-->
        <!--            :class="collapseBtnClass"-->
        <!--            style="cursor: pointer"-->
        <!--            @click="collapse"-->
        <!--          ></span>-->
        <!--        </div>-->
        <!--        <div class="header-userInfo">-->
        <!--          <el-avatar style="vertical-align: middle;margin-right: 8px;" src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"></el-avatar>-->
        <!--          <span style="margin-right: 12px; font-size: 16px;">王小虎</span>-->
        <!--          <el-dropdown>-->
        <!--            <i class="el-icon-arrow-down"></i>-->
        <!--            <el-dropdown-menu slot="dropdown">-->
        <!--              <el-dropdown-item>个人信息</el-dropdown-item>-->
        <!--              <el-dropdown-item>新增</el-dropdown-item>-->
        <!--              <el-dropdown-item>退出</el-dropdown-item>-->
        <!--            </el-dropdown-menu>-->
        <!--          </el-dropdown>-->
        <!--        </div>-->
      </el-header>

      <el-main>
        <!--        展示子路由-->
        <router-view/>
      </el-main>

    </el-container>
  </el-container>
</template>


<script>
import Aside from "../components/Aside";
import Header from "../components/Header";
// import request from "../utils/request"
export default {
  data() {
    return {
      // tableData: Array(10).fill(item),
      // tableData: [],
      // total: 0,
      // currentPage: 1,
      // pageNum: 1,
      // pageSize: 5,
      isCollapse: false,
      // isLoading: false,
      collapseBtnClass: "el-icon-s-fold",
      sideWith: 240,
      menuHeader: true,
      // 搜索框
      // username: '',
      adminName: "管理员"
    };
  },

  components: {
    Aside,
    Header
  },
  methods: {
    collapse() {
      this.isCollapse = !this.isCollapse;
      if(this.isCollapse) {
        this.sideWith = 64
        this.collapseBtnClass='el-icon-s-unfold'
        this.menuHeader=false
      }else{
        this.sideWith = 240
        this.collapseBtnClass='el-icon-s-fold'
        this.menuHeader=true
      }
    },

  },
};
</script>
<style scoped>

</style>
