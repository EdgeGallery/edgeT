<!--
  -  Copyright 2021 Huawei Technologies Co., Ltd.
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
    <div class="pageTitle">
      <el-row>
        <div class="el-row-button-input">
          <label>
            Environment
          </label>
          <el-button
            id="addUserBtn"
            style="float:right;"
            type="primary"
            @click="addProfile"
          >
            <em class="el-icon-user" />
            Add Profile
          </el-button>
        </div>
      </el-row>
    </div>
    <div class="userList">
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
              @selection-change="handleSelectionChange"
            >
              <el-table-column
                type="index"
                :label="'S.No.'"
                width="80"
              />
              <el-table-column
                prop="name"
                label="Name"
              />
              <el-table-column
                label="Operation"
                width="100"
              >
                <template slot-scope="scope">
                  <i
                    id="detailProfileBtn"
                    @click="detailProfile(scope.row)"
                    class="el-icon-document iconCls"
                    title="Detail"
                  />
                  <i
                    id="dltUsrBtn"
                    @click="confirmDeleteProfile(scope.row)"
                    class="el-icon-delete iconCls"
                    title="Delete"
                  />
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
        <el-dialog
          :title="`Profile Name: ${currentRowData.name}`"
          :visible.sync="dialogVisible"
          v-loading="loading"
        >
          <el-row>
            <el-col
              :span="24"
            >
              <el-table
                :data="currentRowData.properties"
                id="editableProfile"
              >
                <el-table-column
                  prop="scenario"
                  label="Scenario"
                  width="150"
                >
                  <template slot-scope="scope">
                    <el-input
                      size="small"
                      style="text-align:center"
                      v-model="scope.row.scenario"
                      controls-position="right"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="testSuiteName"
                  label="TestSuite Name"
                  width="160"
                >
                  <template slot-scope="scope">
                    <el-input
                      size="small"
                      style="text-align:center"
                      v-model="scope.row.testSuiteName"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="testCaseName"
                  label="TestCase Name"
                  width="150"
                >
                  <template slot-scope="scope">
                    <el-input
                      size="small"
                      style="text-align:center"
                      v-model="scope.row.testCaseName"
                      :disabled="scope.$index<addCount"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="inputParameterName"
                  label="InputParameter Name"
                  width="180"
                >
                  <template slot-scope="scope">
                    <el-input
                      size="small"
                      style="text-align:center"
                      v-model="scope.row.inputParameterName"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="value"
                  label="Value"
                  width="120"
                >
                  <template slot-scope="scope">
                    <el-input
                      size="small"
                      style="text-align:center"
                      v-model="scope.row.value"
                    />
                  </template>
                </el-table-column>
                <el-table-column
                  label="Operations"
                  width="120"
                >
                  <template slot-scope="scope">
                    <i
                      @click.native.prevent="deleteRow(scope.$index, scope.row)"
                      class="el-icon-delete iconCls"
                    />
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </el-row>
          <div
            slot="footer"
            class="dialog-footer"
          >
            <el-button
              id="cancel"
              @click="cancel()"
            >
              Cancel
            </el-button>
            <el-button
              id="confirmBtn"
              type="primary"
              @click="confirm()"
              :loading="loading"
            >
              Update
            </el-button>
          </div>
        </el-dialog>
        <add-profile
          :visible="addDialogVisible"
          @getDialogStatus="getDialogStatus"
          @getProfile="getProfileList"
        />
      </div>
    </div>
  </div>
</template>
<script>
import Pagination from '../components/Pagination.vue'
import AddProfile from './AddProfile.vue'
import { profile } from '../tools/request.js'
export default {
  name: 'Profile',
  components: {
    Pagination,
    AddProfile
  },
  data () {
    return {
      selectedRowIds: [],
      isAdmin: false,
      dataLoading: true,
      tableData: [],
      paginationData: [],
      currPageTableData: [],
      dialogVisible: false,
      rowSelection: [],
      selectedNum: 0,
      loading: false,
      dialogLoading: false,
      isAddProfile: false,
      dialogEditVisible: false,
      currentRowData: {
        name: '',
        properties: []
      },
      currentProeprtiesRowData: {
        name: '',
        scenario: '',
        testSuiteName: '',
        testCaseName: '',
        inputParameterName: '',
        value: ''
      },
      originalData: [],
      addDialogVisible: false
    }
  },
  mounted () {
    this.getProfileList()
  },
  computed: {
  },
  methods: {
    getDialogStatus (val) {
      this.addDialogVisible = val
    },
    addProfile () {
      this.currentRowData = {}
      this.addDialogVisible = true
      this.isAddProfile = true
    },
    getCurrentPageData (data) {
      this.currPageTableData = data
    },
    handleSelectionChange (val) {
      this.selectedRowIds = []
      this.rowSelection = val
      this.selectedNum = val.length
      for (let i = 0; i < val.length; i++) {
        if (val[i].role.toLowerCase() === 'administrator') {
          this.isAdmin = true
          return
        }
        this.selectedRowIds.push(val[i].userId)
      }
    },
    detailProfile (usr) {
      this.dialogVisible = true
      this.currentRowData = usr
      let temp = JSON.stringify(usr)
      this.originalData = JSON.parse(temp)
    },
    modifyProfile (user) {
      this.dialogEditVisible = true
      let temp = JSON.stringify(user)
      this.currentProeprtiesRowData = JSON.parse(temp)
      this.tempModifyData = JSON.parse(temp)
    },
    async getProfileList () {
      profile.getProfileList().then(res => {
        this.paginationData = res.data
        this.dataLoading = false
      }, error => {
        if (error) {
          this.$parent.errorMessage(this.$t(error.response.data))
          this.dataLoading = false
        }
      })
    },
    cancel () {
      this.currentRowData = this.originalData
      this.dialogVisible = false
      this.isAddProfile = false
      this.showConfigurationDialog = false
    },
    async confirm () {
      this.cancel()
      let data = {
        name: this.currentRowData.name,
        properties: [this.tempModifyData]
      }
      let tempD = JSON.stringify(this.tempModifyData)
      this.currentProeprtiesRowData = JSON.parse(tempD)
      profile.modifyProfile(data).then(res => {
        this.$parent.successMessage('Modify profile successfully.')
        this.getProfileList()
        this.cancel()
      }, error => {
        if (error) {
          this.$parent.errorMessage('Failed to modify profile.')
        }
      })
    },
    confirmDeleteProfile (row) {
      this.$confirm('Are you sure, you want to delete profile?', 'Delete Profile', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.deleteProfile(row)
      })
    },
    confirmDeleteProperty (row) {
      this.$confirm('Are you sure, you want to delete selected property?', 'Delete Profile', {
        confirmButtonText: 'Confirm',
        cancelButtonText: 'Cancel',
        type: 'warning'
      }).then(() => {
        this.deleteProfile(row)
      })
    },
    async deleteProfile (row) {
      profile.deleteProfile(row.name).then(res => {
        this.$parent.successMessage('Profile deleted successfully.')
        this.getProfileList()
      }, error => {
        if (error) {
          this.$parent.errorMessage('Failed to delete profile.')
        }
      })
    }
  }
}
</script>

<style lang='less' scoped>
.pageTitle {
  margin: 5% 5% 1% 5%;
  font-weight: bold;
}
.userList {
    margin: 0 5%;
    height: 100%;
    background: #fff;
    padding: 30px 0px;
  .tableDiv {
    width: 100%;
  }
  .el-row-button-input {
    margin-top: 10px;
    height:40px;
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
.iconCls {
  padding: 10px;
  cursor: pointer;
}
</style>
