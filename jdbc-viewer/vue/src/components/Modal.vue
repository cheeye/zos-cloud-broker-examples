<template>
  <div data-modal id="modal-ix8wbmx9u7p" class="bx--modal " role="dialog" aria-modal="true" aria-labelledby="modal-ix8wbmx9u7p-label" aria-describedby="modal-ix8wbmx9u7p-heading" tabindex="-1">
    <div class="bx--modal-container">
      <div class="bx--modal-header">
        <!-- <p class="bx--modal-header__label bx--type-delta" id="modal-ix8wbmx9u7p-label">Optional label</p> -->
        <p class="bx--modal-header__heading bx--type-beta" id="modal-ix8wbmx9u7p-heading">Add Data</p>
        <button class="bx--modal-close" type="button" data-modal-close aria-label="close modal" >
          <svg class="bx--modal-close__icon" width="10" height="10" viewBox="0 0 10 10" xmlns="http://www.w3.org/2000/svg">
            <title>Close Modal</title>
            <path d="M6.32 5L10 8.68 8.68 10 5 6.32 1.32 10 0 8.68 3.68 5 0 1.32 1.32 0 5 3.68 8.68 0 10 1.32 6.32 5z" fill-rule="nonzero"
            />
          </svg>
        </button>
      </div>

      <div class="bx--modal-content">
        <div class="bx--grid">
          <div class="bx--row">
            <div>
              <label class="bx--table-header-label">COL1</label>
              <input type="text" class="bx--text-input" placeholder="Enter string" v-model="entryValue.COL1" required/>
            </div>
          </div>
          <div class="bx--row">
            <div>
              <label class="bx--table-header-label">COL2</label>
              <input type="number" class="bx--text-input" placeholder="Enter integer" v-model="entryValue.COL2" required/>
            </div>
          </div>
          <div class="bx--row">
            <div>
              <label class="bx--table-header-label">COL3</label>
              <input type="number" class="bx--text-input" placeholder="Enter integer" v-model="entryValue.COL3" number required/>
            </div>
          </div>
        </div>
      </div>

      <div class="bx--modal-footer">
        <button class="bx--btn bx--btn--primary" type="button" v-on:click="addDataViaGet" data-modal-primary-focus>Submit</button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  Modal
} from 'carbon-components'

export default {
  data: function () {
    return {
      entryValue: {
        COL1: 'foo',
        COL2: 2,
        COL3: 3
      }
    }
  },
  components: {
    Modal
  },
  mounted () {
    // this.getTableData()
    Modal.init()
  },
  methods: {
    async addData () {
      try {
        let resp = await this.$http.post('/schema/tables/' + this.$route.params.tableId, {
          'COL1': this.entryValue.COL1,
          'COL2': this.entryValue.COL2,
          'COL3': this.entryValue.COL3
        })
        console.log(resp.statusCode)
      } catch (err) {
        console.log(err)
      }
    },
    async addDataViaGet () {
      try {
        let resp = await this.$http.get(`/schema/tables/${this.$route.params.tableId}/addData?col1=${this.entryValue.COL1}&col2=${this.entryValue.COL2}&col3=${this.entryValue.COL3}`)
        console.log(resp.statusCode)
        window.location.reload()
      } catch (err) {
        console.log(err)
      }
    }
  }
}
</script>

<style scoped>
.bx--row{
  margin-bottom: 15px;
}
</style>
