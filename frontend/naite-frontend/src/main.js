import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import VueDaumPostcode from 'vue-daum-postcode'

import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'




Vue.use(BootstrapVue)
Vue.use(VueDaumPostcode,{
  scriptUrl: "https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"
})



Vue.config.productionTip = false

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
