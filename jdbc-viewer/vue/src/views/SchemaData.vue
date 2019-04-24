<template>
  <div class="bx--grid">
    <!-- <div class="bx--row">
      <h3>JDBC URL: {{ jdbcUrl }}</h3>
    </div>
    <br> -->
    <div class="bx--row">
      <h2 class="bx-col-xs-12">Schema Data</h2>
    </div>
    <div class="bx--row">
      <p class="bx--col-xs-12">The provisioned Schema contains the following tables and views</p>
    </div>
    <div class="bx--row">
      <div class="bx--col-xs-12 bx--col-lg-6" v-for="table in tables" :key="table">
        <div class="bx--tile">
          <p>
            {{ table }}
            <button class="view-button bx--btn bx--btn--primary" type="button" @click="goToTable(table)">View</button>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data: function () {
    return {
      tables: [],
      jdbcUrl: ''
    }
  },
  mounted () {
    this.getSchemaData()
  },
  methods: {
    async getSchemaData () {
      try {
        console.log("Attempting to retrieve schema data")
        let resp = await this.$http.get('/schema')
        console.log(resp.data)

        this.tables = resp.data.tables
        this.jdbcUrl = resp.data.jdbcUrl
      } catch (err) {
        console.error("Failed to retrieve schema data")
        console.error(err)
      }
    },
    goToTable (tableId) {
      this.$router.push({name: 'table', params: { tableId: tableId }})
    }
  }
}
</script>

<style scoped>
  .bx--row {
    margin-bottom: 10px;
  }
  .view-button {
    float: right;
  }
</style>
