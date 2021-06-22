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
      <div class="rt">
        <el-form
          label-width="120px"
        >
          <el-col
            :span="12"
          >
            <el-form-item
              label="Scenario"
            >
              <el-select
                id="sceanrioSel"
                v-model="scenarioSel"
                :placeholder="'Select Scenario'"
                @change="getTestCaseList"
                style="margin-right: 25px"
              >
                <el-option
                  v-for="item in scenario"
                  :label="item.name"
                  :value="item.name"
                  :key="item.name"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col
            :span="12"
          >
            <el-form-item
              label="Test Suite"
            >
              <el-select
                id="testsuiteSel"
                v-model="testsuiteSel"
                :placeholder="'Select Test Suite'"
                @change="getTestCaseFilter"
              >
                <el-option
                  v-for="item in testSuite"
                  :label="item.name"
                  :value="item.name"
                  :key="item.name"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-form>
      </div>
      <div class="tableDiv">
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
            >
              <el-table-column
                type="index"
                :label="'S.No.'"
                width="80"
              />
              <el-table-column
                prop="testCaseName"
                sortable
                label="TestCase Name"
              />
              <el-table-column
                prop="testSuiteName"
                label="TestSuite Name"
              />
              <el-table-column
                label="Operation"
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
    <TestCaseDetail
      :data="testCaseDetail"
      :dialog-visible="dialogVisible"
      @getDialogStatus="getDialogStatus"
    />
  </div>
</template>

<script>
import { scenario } from '../tools/request.js'
import Pagination from '../components/Pagination.vue'
import TestCaseDetail from './TestCaseDetails.vue'
export default {
  name: 'TestCase',
  components: {
    Pagination,
    TestCaseDetail
  },
  data () {
    return {
      dataLoading: false,
      tableData: [],
      paginationData: [],
      currPageTableData: [],
      rowSelection: [],
      currentRowData: '',
      loading: false,
      language: localStorage.getItem('language'),
      scenario: [],
      scenarioSel: '',
      testSuite: [],
      testsuiteSel: '',
      testCaseDetail: {},
      dialogVisible: false
    }
  },
  mounted () {
    this.getScenarioList()
  },
  computed: {
    totalNum: function () {
      return this.tableData.length
    }
  },
  watch: {
  },
  methods: {
    getDialogStatus (val) {
      this.dialogVisible = val
    },
    // 对app表格进行筛选 val：需要查询的值  key: 数据对应的字段
    filterTableData (val, key) {
      this.paginationData = this.paginationData.filter(item => {
        if (item[key] !== null) {
          let itemType = item[key].toLowerCase()
          return itemType.indexOf(val) > -1
        }
      })
    },
    // 根据分页组件显示数据
    getCurrentPageData (data) {
      this.currPageTableData = data
    },
    async getScenarioList () {
      scenario.getScenarioList().then(response => {
        this.scenario = response.data
        this.scenarioSel = this.scenario[0].name
        this.getTestCaseList()
        this.getTestSuiteList()
      }).catch(() => {
        this.$message.error('Failed to get scenario.')
      })
    },
    async getTestSuiteList () {
      scenario.getTestSuite(this.scenarioSel).then(response => {
        this.testSuite = []
        this.testSuite = response.data
      }).catch(() => {
        this.$message.error('Failed to get test suites.')
      })
    },
    getTestCaseFilter () {
      this.paginationData = []
      this.paginationData = this.tableData.filter(item => {
        if (item.testSuiteName === this.testsuiteSel) {
          return true
        }
      })
    },
    async getTestCaseList () {
      this.dataLoading = true
      scenario.getScenarioTestCases(this.scenarioSel).then(response => {
        this.dataLoading = false
        this.tableData = response.data
        this.paginationData = this.tableData
      }).catch(() => {
        this.dataLoading = false
        this.$message.error('Failed to get test cases.')
      })
    },
    async checkDetail (val) {
      this.dataLoading = true
      scenario.getTestSuiteTestCases(this.scenarioSel, val.testSuiteName, val.testCaseName).then(response => {
        this.dialogVisible = true
        this.dataLoading = false
        this.testCaseDetail = response.data[0]
      }).catch(() => {
        this.dataLoading = false
        this.$message.error('Failed to get test cases.')
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
    margin-top: 50px;
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
