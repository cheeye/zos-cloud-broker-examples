<template>
  <div id="header">
    <span class="logo bx--type-gamma">JDBC Viewer for Db2</span>
  </div>
</template>

<script>
export default {
  name: 'HeaderLite'
}
</script>

<style lang="scss">
  @import "./../../node_modules/carbon-components/scss/globals/scss/_spacing.scss";
  @import "./../../node_modules/carbon-components/scss/globals/scss/_colors.scss";
  #header {
    display: flex;
    align-items: center;
    height: $layout-lg;
    background-color: $color__blue-51;
    padding-left: $layout-md;
    margin-bottom: $layout-sm;
  }

  .logo {
    color: $color__white;
  }
</style>
