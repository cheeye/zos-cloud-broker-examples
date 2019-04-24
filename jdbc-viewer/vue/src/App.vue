<template>
  <div id="app">

    <NavHeader/>

    <router-view />
  </div>
</template>

<script>
import NavHeader from './components/NavHeader.vue'

export default {
  components: {
    NavHeader
  },
  data: function () {
    return {
    }
  }
}
</script>
