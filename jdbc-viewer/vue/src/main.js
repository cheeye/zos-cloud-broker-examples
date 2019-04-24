import Vue from 'vue'
import App from './App.vue'
import router from './router'

import Axios from 'axios'
import 'carbon-components/css/carbon-components.min.css'

const axios = Axios.create({
  baseURL: `http://${process.env.VUE_APP_API_HOST}:${process.env.VUE_APP_API_PORT}/JdbcViewerJaxRS/api/`
})
axios.defaults.headers.post['Content-Type'] = 'application/json'
Vue.prototype.$http = axios

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
