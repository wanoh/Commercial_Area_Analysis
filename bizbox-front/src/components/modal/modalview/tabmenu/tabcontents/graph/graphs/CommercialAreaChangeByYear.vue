<template>
  <div class="secition-content">
    <div class="secition-content-title-area">
      <h2 class="section-content-title">
        연도별 상권 변화 지표
        <span class="icon-question" @click="popup">
          <v-icon size="15">mdi-help-circle-outline</v-icon>
          <span v-show="popflag" class="icon-popup-tri" />
        </span>
        <span v-show="popflag" class="icon-popup">공공데이터 상권 관련 데이터를 분석해서 생성한 정보입니다.</span>
      </h2>
      <div class="section-content-update">2019-2분기 업데이트</div>
    </div>
    <p class="point-content-area Content" style="font-size: 1.2em;
    font-weight: bold;">
      <span class="point-normal">전년대비</span>
      <span class="point-title">{{maxAgeMaker}}</span>
      <span class="point-percent">{{percentMaker}}</span>
      <span class="point-normal">했습니다</span>
    </p>
    <div id="chart">
      <loading :loading="loadingStatus" :transparent='true'></loading>
      <bar-chart :chart-data="chartdata" :options="chartoptions" width="480px" height="300px"></bar-chart>
    </div>
  </div>
</template>

<script>
import BarChart from '@/lib/BarChart'
import axios from '@/js/http-commons'
import Loading from '@/components/common/loading/Loading'
import './Graphs.css'
import { eventBus } from '@/js/bus'
export default {
  components: {
    BarChart,
    Loading
  },
  data () {
    return {
      popflag: false,
      chartdata: null,
      chartoptions: null,
      result: null,
      road: '',
      key: this.$store.state.modalsearch,
      searchOption: 1,
      title: '연도별 상권 변화 지표',
      point: 0,
      btnStyle1: {
        backgroundColor: '#d9d9d9',
        cursor: 'pointer'
      },
      btnStyle2: {
        backgroundColor: 'white',
        cursor: 'pointer'
      },
      btnStyle3: {
        backgroundColor: 'white',
        cursor: 'pointer'
      },
      btnStyle4: {
        backgroundColor: 'white',
        cursor: 'pointer'
      },
      chartStyle: {
        display: 'contents'
      },
      loadingStatus: false,
      allowDiv: {
        display: 'none'
      }
    }
  },
  computed: {
    percentMaker: function () {
      if (this.result == null) return
      let preYear = this.result[4].g
      let thisYear = this.result[5].g * 2
      let percent = ((thisYear - preYear) / preYear) * 100
      return '(' + Math.round(percent * 100) / 100 + '%' + ')'
    },
    maxAgeMaker: function () {
      if (this.result == null) return
      let preYear = this.result[4].g
      let thisYear = this.result[5].g * 2
      if (preYear >= thisYear) {
        return '하강'
      } else {
        return '상승'
      }
    }
  },
  mounted () {
    this.draw()
    eventBus.$on('clickmap', name => {
        this.key = name
        this.draw()
    })
  },
  created () {
    // eventBus.$on('clickmap', name => {
    //   this.draw()
    // })
  },
  methods: {
    popup () {
      this.popflag = !this.popflag
    },
    draw () {
      this.chartdata = null
      this.chartoptions = null

      this.searchOption = 4
      this.title = '연도별 상권 변화 지표'
      this.btnStyle1.backgroundColor = 'white'
      this.btnStyle2.backgroundColor = 'white'
      this.btnStyle3.backgroundColor = 'white'
      this.btnStyle4.backgroundColor = '#d9d9d9'

      if (this.key !== '') {
        this.getData()
      }
    },
    getData () {
      this.loadingStatus = true
      this.allowDiv.display = 'block'
      this.btnStyle1.cursor = 'not-allowed'
      this.btnStyle2.cursor = 'not-allowed'
      this.btnStyle3.cursor = 'not-allowed'
      this.btnStyle4.cursor = 'not-allowed'
      axios
        .get('/change/getHistory/' + this.key)
        .then(res => {
          this.result = res.data.cblist
          this.road = this.result[0].d
          this.point = res.data.point
        })
        .finally(() => {
          this.chartdata = {
            labels: ['2014', '2015', '2016', '2017', '2018', '2019-2'],
            datasets: [
              {
                label: '운영 영업 개월 평균',
                backgroundColor: '#74ddf7',
                data: [
                  this.result[0].g,
                  this.result[1].g,
                  this.result[2].g,
                  this.result[3].g,
                  this.result[4].g,
                  this.result[5].g * 2
                ]
              },
              {
                label: '폐업 영업 개월 평균',
                backgroundColor: '#ff6390',
                data: [
                  this.result[0].h,
                  this.result[1].h,
                  this.result[2].h,
                  this.result[3].h,
                  this.result[4].h,
                  this.result[5].h * 2
                ]
              }
            ]
          }

          this.chartoptions = {
            responsive: false,
            maintainAspectRatio: false,
            scales: {
              yAxes: [
                {
                  ticks: {
                    beginAtZero: true
                  },
                  gridLines: {
                    display: true
                  }
                }
              ],
              xAxes: [
                {
                  gridLines: {
                    display: true
                  }
                }
              ]
            }
          }

          this.loadingStatus = false
          this.allowDiv.display = 'none'

          this.btnStyle1.cursor = 'pointer'
          this.btnStyle2.cursor = 'pointer'
          this.btnStyle3.cursor = 'pointer'
          this.btnStyle4.cursor = 'pointer'
        })
    }
  }
}
</script>

<style scoped lang="scss">
#chart {
  position: relative;
  width: 500px;
  height: 300px;
  overflow: hidden;
}

#back {
  position: absolute;
  z-index: 2;
  width: 100%;
  height: 100%;
  background-color: rgb(255, 255, 255);
}

#point {
  border: 1px solid black;
  border-radius: 5px;
  width: 500px;
  height: 40px;
  line-height: 40px;
  top: 5px;
  font-size: 24px;
  margin-top: 10px;
  background-color: white;
}

#searchOptions {
  margin: auto;

  button {
    font-size: 16px;
    display: inline-block;
    border: 1px solid black;
    border-radius: 5px;
    width: 200px;
    height: 30px;
    margin-right: 10px;
    background-color: white;
    transition: 0.1s all ease;

    &:hover {
      font-weight: bold;
    }
  }
}

#searchOptions button:hover {
  background-color: #e38fe3;
}

#search input {
  border: 1px solid black;
  border-radius: 5px 0 0 5px;
  width: 200px;
  height: 30px;
  margin-top: 10px;
  padding: 5px;
  background-color: white;
}

#search-btn {
  border-radius: 0 5px 5px 0;
  width: 50px;
  height: 30px;
  background-color: rgb(172, 172, 172);
}

#search-result {
  margin-top: 5px;
}

$color1: rgb(232, 113, 91);
$color2: rgb(15, 66, 95);

.Content {
  width: 100%;
  padding: 10px 20px;
  margin: 20px 0;
  background-color: $color2;
  border-radius: 5px;
  color: $color1;
  box-shadow: 2px 3px 5px rgba(0, 0, 0, 0.5);
  text-align: center;

  .strong {
    color: rgb(223, 223, 223);
  }

  h2 {
    font-size: 1.2em;
    font-weight: bold;
  }
}
</style>
