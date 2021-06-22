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
      <div>
        <el-select
          id="sceanrioSel"
          v-model="scenarioSel"
          :placeholder="'Select Scenario'"
          @change="getTestSuiteList"
          style="margin-right: 25px"
        >
          <el-option
            v-for="item in scenario"
            :label="item.name"
            :value="item.name"
            :key="item.name"
          />
        </el-select>
        <el-input
          type="textarea"
          v-model="scenarioDesc"
          style="margin-right: 25px; width:400px"
          placeholder="Scenario Description"
          readonly
        />
        <el-select
          id="testsuiteSel"
          v-model="testsuiteSel"
          :placeholder="'Select Test Suite'"
          @change="getTestCaseList"
          style="margin-right: 25px"
        >
          <el-option
            v-for="item in testSuite"
            :label="item.name"
            :value="item.name"
            :key="item.name"
          />
        </el-select>
        <el-input
          type="textarea"
          v-model="testSuiteDesc"
          style="margin-right: 25px; width:400px"
          placeholder="Test Suite Description"
          readonly
        />
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
  name: 'TestCase',
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
      currentRowData: '',
      loading: false,
      language: localStorage.getItem('language'),
      scenario: [],
      scenarioSel: '',
      testSuite: [],
      testsuiteSel: '',
      scenarioDesc: '',
      testSuiteDesc: ''

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
    '$i18n.locale': function () {
      let language = localStorage.getItem('language')
      this.language = language
      this.getScenarioList()
    }
  },
  methods: {
    // 对app表格进行筛选 val：需要查询的值  key: 数据对应的字段
    filterTableData (val, key) {
      this.paginationData = this.paginationData.filter(item => {
        if (item[key] !== null) {
          let itemType = item[key].toLowerCase()
          return itemType.indexOf(val) > -1
        }
      })
    },
    getScenarioDesc () {
      this.scenario.forEach(item => {
        if (item.name === this.scenarioSel) {
          this.scenarioDesc = item.desc
        }
      })
    },
    getTestSuiteDesc () {
      this.testSuite.forEach(item => {
        if (item.name === this.testsuiteSel) {
          this.testSuiteDesc = item.desc
        }
      })
    },
    getCurrentPageData (data) {
      this.currPageTableData = data
    },
    async getScenarioList () {
      scenario.getScenarioInfo().then(response => {
        this.scenario = response.data
      }).catch(() => {
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    async getTestSuiteList () {
      this.getScenarioDesc()
      this.testsuiteSel = ''
      this.testSuiteDesc = ''
      scenario.getTestSuiteInfo().then(response => {
        this.testSuite = []
        response.data.forEach(item => {
          if (item.scenario === this.scenarioSel) {
            this.testSuite.push(item)
          }
        })
      }).catch(() => {
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    async getTestCaseList () {
      this.getTestSuiteDesc()
      this.dataLoading = true
      scenario.getTestCaseInfo().then(response => {
        this.dataLoading = false
        this.tableData = []
        response.data.forEach(item => {
          if (item.scenario === this.scenarioSel && item.testsuite === this.testsuiteSel) {
            this.tableData.push(item)
          }
        })
        this.paginationData = this.tableData
        this.checkProjectData()
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
