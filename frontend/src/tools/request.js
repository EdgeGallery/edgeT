import axios from 'axios'

let api = 'http://localhost:8088/v1/vtp'

axios.interceptors.response.use(
  function (response) {
    return response
  },
  function (error) {
    if (error.response.status === 401) {
      console.log('failed')
      setTimeout(() => {
        window.location.href = api
      }, 1500)
    }
    return Promise.reject(error)
  }
)

function GET (url, params) {
  return axios.get(url, {
    params: params
  })
}

function POST (url, params) {
  return axios.post(url, params)
}

function PUT (url, params) {
  return axios.put(url, params)
}

function DELETE (url, params) {
  return axios.delete(url, {
    data: params
  })
}

let scenario = {
  getScenarioList () {
    return GET(api + '/scenarios')
  },
  getTestSuite (scenario) {
    return GET(api + '/scenarios/' + scenario + '/testsuites')
  },
  getScenarioTestCases (scenario) {
    return GET(api + '/scenarios/' + scenario + '/testcases')
  },
  getTestSuiteTestCases (scenario, testsuite, testcase) {
    return GET(api + '/scenarios/' + scenario + '/testsuites/' + testsuite + '/testcases/' + testcase)
  }
}

let profile = {
  getProfileList () {
    return GET(api + '/profiles')
  },
  addProfile (data) {
    return POST(api + '/profiles', data)
  },
  modifyProfile (data) {
    return PUT(api + '/profiles/' + data.name, data.properties)
  },
  deleteProfile (name) {
    return DELETE(api + '/profiles/' + name)
  }
}

let execution = {
  getExecutions () {
    return GET(api + '/executions')
  },
  addExecution (query) {
    return POST(api + '/executions', query)
  },
  getSingleExecution (id) {
    return GET(api + '/executions/ebaa5f21-ed68-4098-97a9-775ac8800f09-1622027059843')
  }
}

let tester = {
  getTesterList () {
    return GET(api + '/manager/testers')
  }
}

export {
  GET,
  POST,
  PUT,
  DELETE,
  scenario,
  profile,
  execution,
  tester
}
