import Vue from 'vue'
import Router from 'vue-router'
// import Home from './views/Home.vue'

import SchemaData from './views/SchemaData.vue'
import TableData from './views/TableData.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'schema',
      component: SchemaData
    },
    {
      path: '/table/:tableId',
      name: 'table',
      component: TableData
    }
  ]
})
