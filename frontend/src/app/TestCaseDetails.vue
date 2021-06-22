<template>
  <div>
    <el-dialog
      :visible.sync="dialogVisible"
      width="30%"
    >
      <span slot="title">
        <span class="el-dialog__title">{{ data.testCaseName }}</span>
        <el-button
          type="default"
          @click="dialogEditorVisible = true; dialogVisible = false"
          size="small"
          style="margin-left: 50%"
        >
          Run
        </el-button>
      </span>
      <div style="margin-top: 15px;">
        <el-row>
          <el-col :span="6">
            <span style="float:right">Description: </span>
          </el-col>
          <el-col :span="18">
            <span style="font-weight:bold">{{ data.description }}</span>
          </el-col>
        </el-row>
      </div>
      <div style="margin-top: 15px;">
        <label
          v-for="item in data.inputs"
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
            <span style="float:right">Results: </span>
          </el-col>
          <el-col :span="18">
            <span style="font-weight:bold">{{ data.outputs ? data.outputs[0].name: '' }}</span>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
    <el-dialog
      :title="`Running ${ data.testCaseName }`"
      :visible.sync="dialogEditorVisible"
      width="40%"
      style="padding: 0px 80px"
    >
      <el-form
        class="apply-form
        first-form"
        ref="form"
        style="padding:0px 40px"
      >
        <div
          style="margin-top: 15px;"
          v-for="item in data.inputs"
          :key="item.name"
        >
          <el-form-item
            :label="item.name"
          >
            <el-input
              v-if="item.type === 'string'"
              :placeholder="item.name"
              v-model="item.defaultValue"
              type="text"
              style="width:50%"
            />
            <el-input
              v-if="item.type === 'url'"
              :placeholder="item.name"
              v-model="item.defaultValue"
              style="width:50%"
              type="url"
            />
            <el-checkbox
              v-if="item.type === 'bool'"
              style="width:50%"
            >
              {{ item.name }}
            </el-checkbox>
            <el-input-number
              v-if="item.type === 'digit'"
              v-model="item.defaultValue"
              :min="1"
              :max="10"
              style="width:50%"
            />
            <input
              v-if="item.type === 'binary'"
              type="file"
              class="el-input__inner"
              formControlName="warFile"
              @change="beforeUpload($event)"
              :placeholder="item.name"
              style="width:50%"
            >
          </el-form-item>
        </div>
        <span>Select Profile </span>
        <el-select
          v-model="value"
          placeholder="Select Profile"
        >
          <el-option
            v-for="item in ProfileNames"
            :key="item.name"
            :label="item.name"
            :value="item.name"
          />
        </el-select>
      </el-form>

      <div
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          id="cancelBtn"
          @click="cancelEditor()"
        >
          Cancel
        </el-button>
        <el-button
          id="confirmBtn"
          type="primary"
          @click="confirm()"
        >
          Run
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { execution, profile } from '../tools/request.js'
export default {
  name: 'TestCaseDetail',
  props: {
    data: {
      type: Object,
      default: () => {},
      isRequired: false
    },
    dialogVisible: {
      type: Boolean,
      default: false
    }
  },
  watch: {
    dialogVisible (val) {
      this.$emit('getDialogStatus', val)
    }
  },
  mounted () {
    this.getProfileList()
  },
  data () {
    return {
      dialogEditorVisible: false,
      ProfileNames: []
    }
  },
  methods: {
    cancelEditor () {
      this.dialogEditorVisible = false
    },
    async getProfileList () {
      profile.getProfileList().then(res => {
        this.ProfileNames = res.data
      }, error => {
        if (error) {
          this.$parent.errorMessage(this.$t(error.response.data))
        }
      })
    },
    confirm () {
      execution.addExecution(this.data.inputs).then(res => {
      }, error => {
        if (error) {
          this.$parent.errorMessage(this.$t(error.response.data))
        }
      })
    }
  }
}
</script>
<style>
  .upload-box{
    border: 1px solid gainsboro;
    padding: 8px;
    width: 100%;
  }
</style>
