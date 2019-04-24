<template>
  <div class="bx--grid">
    <button class="bx--btn bx--btn--primary add-button" type="button" data-modal-target="#modal-ix8wbmx9u7p">Add Data</button>
    <Modal />
    <br>
    <br>
    <div class="bx--row">
      <!-- <div class="bx--data-table-v2-container bx--tile data-table-v2"> -->
      <div class="bx--tile bx--data-table-v2-container">
        <h4 class="bx--data-table-v2-header">{{title}}</h4>
        <br>
        <table v-if="columnNames.length !== 0" class="bx--data-table-v2 bx--data-table-v2--zebra">
          <thead>
            <tr>
              <th v-for="colName in columnNames" :key="colName">
                <span class="bx--table-header-label">{{colName}}</span>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="row in rows" :key="row">
              <td v-for="col in Object.keys(row)" :key="col">{{row[col]}}</td>
            </tr>
          </tbody>
        </table>
        <h5 v-else>No data in current table/view</h5>
      </div>
    </div>
  </div>
</template>

<script>
import Modal from '@/components/Modal.vue'

export default {
  data: function () {
    return {
      title: this.$route.params.tableId,
      columnNames: [],
      rows: []
    }
  },
  components: {
    Modal
  },
  mounted () {
    this.getTableData()
  },
  methods: {
    async getTableData () {
      try {
        let resp = await this.$http.get('/schema/tables/' + this.$route.params.tableId)
        console.log(resp.data)

        this.columnNames = Object.keys(resp.data[0])
        this.rows = resp.data
      } catch (err) {
        console.error(err)
      }
    },
    async postSubmit () {
      console.log(JSON.stringify(this.entryValue))
    }
  }
}
</script>

<style>
  .add-button {
    margin-left: -10px;
  }
</style>
