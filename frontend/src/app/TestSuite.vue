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
            :span="24"
          >
            <el-form-item
              label="Scenario"
            >
              <el-select
                id="sceanrioSel"
                v-model="scenarioSel"
                :placeholder="'Select Scenario'"
                @change="getTestSuiteList"
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
  name: 'TestSuite',
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
      scenarioSel: '',
      language: localStorage.getItem('language'),
      scenario: [],
      testSuite: []
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
        this.getTestSuiteList(this.scenarioSel)
      }).catch(() => {
        this.$message.error(this.$t('tip.failedToGetAppList'))
      })
    },
    async getTestSuiteList (val) {
      this.dataLoading = true
      scenario.getTestSuite(val).then(response => {
        this.dataLoading = false
        this.tableData = response.data
        this.paginationData = this.tableData
      }).catch(() => {
        this.dataLoading = false
        this.$message.error('Failed to get test suites.')
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
