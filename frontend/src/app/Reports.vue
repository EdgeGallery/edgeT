<template>
  <div>
    <div class="apacList">
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
                sortable
                label="Execution"
              >
                <template slot-scope="scope">
                  <a
                    href="#"
                    style="color: #73757b"
                    @click="getCurrentRow(scope.row); return false;"
                  >{{ scope.row.executionId }}</a>
                </template>
              </el-table-column>
              <el-table-column
                prop="startTime"
                label="End Time"
              />
              <el-table-column
                prop="endTime"
                label="End Time"
              />
              <el-table-column
                prop="status"
                label="Status"
              />
              <el-table-column
                prop="results.error"
                label="Results"
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
    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span slot="title">
        <span class="el-dialog__title">Test Result {{ currentRowData.testCaseName }}</span>
      </span>
      <div style="margin-top: 15px;">
        <el-row>
          <el-col :span="6">
            <span style="float:right">Status: </span>
          </el-col>
          <el-col :span="18">
            <span style="font-weight:bold">{{ currentRowData.status }}</span>
          </el-col>
        </el-row>
      </div>
      <div style="margin-top: 15px;">
        <label
          v-for="item in currentRowData.parameters"
          :key="item.name"
        >
          <br>
          <el-row>
            <el-col :span="6">
              <span style="float:right">{{ item.name }}: </span>
            </el-col>
            <el-col :span="18">
              <span style="font-weight:bold">{{ item.defaultValue }}</span>
            </el-col>
          </el-row>
        </label>
      </div>
      <div style="margin-top: 15px;">
        <el-row>
          <el-col :span="6">
            <span style="float:right">Result Details: </span>
          </el-col>
          <el-col :span="18">
            <span style="font-weight:bold">{{ currentRowData.results ? currentRowData.results.error : '' }}</span>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { execution } from '../tools/request.js'
import Pagination from '../components/Pagination.vue'
export default {
  name: 'Reports',
  components: {
    Pagination
  },
  data () {
    return {
      dataLoading: false,
      tableData: [],
      paginationData: [],
      currPageTableData: [],
      dialogVisible: false,
      rowSelection: [],
      selectedNum: 0,
      selectedNodeNum: 0,
      currentRowData: {},
      loading: false,
      dialogLoading: false,
      language: localStorage.getItem('language')
    }
  },
  mounted () {
    this.getExecution()
  },
  computed: {
    totalNum: function () {
      return this.tableData.length
    }
  },
  watch: {
  },
  methods: {
    // 根据分页组件显示数据
    getCurrentPageData (data) {
      this.currPageTableData = data
    },
    getCurrentRow (val) {
      this.currentRowData = val
      this.dialogVisible = true
    },
    async getExecution (val) {
      this.dataLoading = true
      execution.getExecutions().then(response => {
        this.tableData = response.data
        this.paginationData = this.tableData
        this.dataLoading = false
      }).catch(() => {
        this.dataLoading = false
        this.$message.error('Failed to get testers.')
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
