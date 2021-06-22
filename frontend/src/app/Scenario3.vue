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
  <div>
    <div class="apacList">
      <div
        class="tableDiv"
      >
        <el-row>
          <el-col
            :span="24"
            class="table"
          >
            <el-table
              v-loading="dataLoading"
              :data="currPageTableData"
              border
              size="small"
              style="width: 100%;"
              @expand-change="getTestSuiteList"
              ref="tableData"
            >
              <el-table-column
                type="expand"
              >
                <template>
                  <el-row>
                    <el-col
                      :span="24"
                      class="table"
                    >
                      <el-table
                        id="testSuiteTable"
                        :data="testSuite"
                        border
                        size="small"
                        style="width: 100%;"
                        @expand-change="getTestCaseList"
                        ref="testSuiteTable"
                      >
                        <el-table-column
                          type="expand"
                        >
                          <template>
                            <el-table
                              id="testCaseTable"
                              :data="testCase"
                              border
                              size="small"
                              style="width: 100%;"
                            >
                              <el-table-column
                                prop="name"
                                sortable
                                :label="$t('app.packageList.name')"
                              />
                              <el-table-column
                                prop="desc"
                                :label="$t('app.packageList.desc')"
                              />
                              <el-table-column
                                :label="$t('app.packageList.operation')"
                                align="center"
                              >
                                <template slot-scope="scope">
                                  <el-button
                                    id="detailBtn"
                                    @click="checkDetail(scope.row)"
                                    size="small"
                                  >
                                    Run
                                  </el-button>
                                </template>
                              </el-table-column>
                            </el-table>
                          </template>
                        </el-table-column>
                        <el-table-column
                          prop="name"
                          sortable
                          :label="$t('app.packageList.name')"
                        />
                        <el-table-column
                          prop="desc"
                          :label="$t('app.packageList.desc')"
                        />
                      </el-table>
                    </el-col>
                  </el-row>
                </template>
              </el-table-column>
              <el-table-column
                prop="name"
                sortable
                :label="$t('app.packageList.name')"
              />
              <el-table-column
                prop="desc"
                :label="$t('app.packageList.desc')"
              />
            </el-table>
          </el-col>
        </el-row>
        <div class="pageBar">
          <Pagination
            :page-sizes="[10,15,20,25]"
            :table-data="paginationData"
            @getCurrentPageData="getCurrentPageData"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { scenario } from '../tools/request.js'
import Pagination from '../components/Pagination.vue'
export default {
  name: 'Scenario',
  components: {
    Pagination
  },
  data () {
    return {
      dataLoading: false,
      tableData: [],
      paginationData: [],
      currPageTableData: [],
      rowSelection: [],
      selectedNum: 0,
      selectedNodeNum: 0,
      currentRowData: '',
      loading: false,
      language: localStorage.getItem('language'),
      testSuite: [],
      expanded: '',
      testCase: []
    }
  },
  mounted () {
    this.appType = this.$route.query.type ? this.$route.query.type : ''
    this.getScenarioList()
  },
  computed: {
    edgeNodeTotalNum: function () {
      return this.edgeNodesData.length
    },
    totalNum: function () {
      return this.tableData.length
    },
    currPageEdgeNodeTableData: function () {
      return this.edgeNodesData.filter(data => !this.edgeNodeSearchInput || data.mechostName.toLowerCase().includes(this.edgeNodeSearchInput.toLowerCase()))
    }
  },
  watch: {
    '$i18n.locale': function () {
      let language = localStorage.getItem('language')
      this.language = language
      this.getScenarioList()
    }
  },
  methods: {
    getTestSuiteList (row, expanded) {
      if (document.getElementsByClassName('el-table__expand-icon--expanded').length > 0) {
        if (expanded.length > 0) {
          document.getElementsByClassName('el-table__expand-icon--expanded')[0].click()
        }
      }
      scenario.getTestSuiteInfo().then(response => {
        this.testSuite = []
        response.data.forEach(item => {
          if (item.scenario === row.name) {
            this.testSuite.push(item)
          }
        })
      }).catch(() => {
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    getTestCaseList (row, expanded) {
      let self = this
      if (expanded.length === 0) {
        self.$refs.testSuiteTable.toggleRowExpansion(row, false)
      } else {
        self.$refs.testSuiteTable.toggleRowExpansion(row, true)
      }
      scenario.getTestCaseInfo().then(response => {
        this.testCase = []
        response.data.forEach(item => {
          if (item.testsuite === row.name) {
            this.testCase.push(item)
          }
        })
      }).catch(() => {
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    filterTableData (val, key) {
      this.paginationData = this.paginationData.filter(item => {
        if (item[key] !== null) {
          let itemType = item[key].toLowerCase()
          return itemType.indexOf(val) > -1
        }
      })
    },
    // 根据搜索组件进行筛选
    getSearchData (data) {
      this.paginationData = this.tableData
      if (this.paginationData && this.paginationData.length > 0) {
        let reset = false
        for (let key in data) {
          if (data[key]) {
            reset = true
            this.filterTableData(data[key].toLowerCase(), key)
          }
        }
        if (!reset) this.paginationData = this.tableData
      }
    },
    // 根据分页组件显示数据
    getCurrentPageData (data) {
      this.currPageTableData = data
    },
    async getScenarioList () {
      this.dataLoading = true
      scenario.getScenarioInfo().then(response => {
        this.tableData = response.data
        this.paginationData = this.tableData
        this.checkProjectData()
        if (this.appType) this.filterTableData(this.appType, 'type')
        this.dataLoading = false
      }).catch(() => {
        this.dataLoading = false
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    checkProjectData () {
      this.tableData.forEach(itemBe => {
      })
    }
  }
}
</script>

<style lang='less' scoped>
.apacList {
    margin: 0 5%;
    height: 100%;
    background: #fff;
    padding: 100px 60px  10px 60px;
  .tableDiv {
    width: 100%;
  }
  .el-row-button-input {
    margin-top: 10px;
  }
  .table {
    margin-top: 15px;
  }
  .el-row-table {
    margin-top: 10px;
  }
  .shortdesc{
    overflow: hidden;
    text-overflow: ellipsis;
    display:-webkit-box;
    -webkit-box-orient:vertical;
    -webkit-line-clamp:2;
  }
}
</style>
