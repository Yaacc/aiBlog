import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    // name: 'Manage',
    component: ()=>import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      { path: 'home', name: 'Home', component: ()=>import('../views/Home.vue')},
      { path: 'user', name: 'User', component: ()=>import('../views/User.vue')},
      { path: 'admin', name: 'Admin',component:()=>import('../views/Admin.vue')},
      { path: 'personal',name: 'Personal',component:()=>import('../views/Personal')},
      { path: 'files',name: 'Files',component:()=>import('../views/Files.vue')}
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  }

]

const router = new VueRouter({
  // routes: [
  //     {path: '/helloworld', component: Hello},
  // ],
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next)=>{
  next()
})
export default router
