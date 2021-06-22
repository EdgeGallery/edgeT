<!--
  -  Copyright 2020 Huawei Technologies Co., Ltd.
  -
  -  Licensed under the Apache License, Version 2.0 (the "License");
  -  you may not use this file except in compliance with the License.
  -  You may obtain a copy of the License at
  -
  -      http://www.apache.org/licenses/LICENSE-2.0
  -
  -  Unless required by applicable law or agreed to in writing, software
  -  distributed under the License is distributed on an "AS IS" BASIS,
  -  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  -  See the License for the specific language governing permissions and
  -  limitations under the License.
  -->

<template>
  <div class="my-pagination">
    <el-pagination
      background
      class="rt"
      @size-change="handlePageSizeChange"
      @current-change="handleCurrentPageChange"
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="totalNum"
      v-if="totalNum!=0"
    />
  </div>
</template>

<script>
export default {
  props: {
    tableData: {
      required: true,
      type: Array
    },
    pageSizes: {
      required: true,
      type: Array
    }
  },
  data () {
    return {
      pageSize: 10,
      totalNum: 0,
      currentPage: 1,
      data: []
    }
  },
  watch: {
    tableData (val) {
      this.data = val
      this.totalNum = val.length
      this.returnTableData()
      // let page = sessionStorage.getItem('currentPage') ? Number(sessionStorage.getItem('currentPage')) : 1
      // this.handleCurrentPageChange(page)
    }
  },

  methods: {
    handlePageSizeChange (val) {
      this.pageSize = val
      this.returnTableData()
    },
    handleCurrentPageChange (val) {
      this.currentPage = val
      this.returnTableData()
      // sessionStorage.setItem('currentPage', val)
    },
    returnTableData () {
      let start = (this.currentPage - 1) * this.pageSize
      let end = this.currentPage * this.pageSize
      let currentPageData = this.data.slice(start, end)
      this.$emit('getCurrentPageData', currentPageData)
    }
  },
  mounted () {}
}

</script>
