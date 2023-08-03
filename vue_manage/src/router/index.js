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
      { path: 'employee', name: 'Employee', component: ()=>import('../views/Employee.vue')},
      { path: 'admin', name: 'Admin',component:()=>import('../views/Admin.vue')},
      { path: 'home', name: 'Home', component: ()=>import('../views/Home.vue')},
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
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
