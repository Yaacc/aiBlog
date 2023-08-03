// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/style.css'
import router from './router'
import request from "./utils/request.js"

Vue.config.productionTip = false
Vue.use(ElementUI);
// ,{size:"small"}

// 全局request
Vue.prototype.request=request
/* eslint-disable no-new */
new Vue({
  router,
  el: '#app',
  components: { App },
  template: '<App/>'
})
