<template>
  <div class="index">
    <headers></headers>
    <el-carousel height="500px">
      <el-carousel-item v-for="(item,index) in rotations" :key="index">
        <img style="width:100%;height:100%" :src="$store.state.HOST + item.image">
      </el-carousel-item>
    </el-carousel>
    <div class="index1">
      <div class="index2">
        <div class="index3">
          我的AI课程知识点推荐
        </div>
        <div class="index4">
          <div class="index5" v-for="(item,index) in hotels" :key="index" >
            <img style="width:100%;height:250px" :src="item.images.split(',')[0]">
            <div class="index6">
              <div class="index7">{{item.name}}</div>
              <div class="index8">{{item.introduce}}</div>
            </div>
            <div class="index9" style="margin-top:7px;cursor: pointer" @click="toInfo(item.id)">查看</div>
          </div>
        </div>
        <el-button style="margin-top:35px" size="small" type="primary" plain @click="toAttraction">了解更多</el-button>
      </div>
    </div>
    <div class="index10">
    </div>
    <div class="index11">

    </div>
    <div class="index1">
      <div class="index2" >
        <div class="index3">
         AI课程学习路线
        </div>
        <div class="index4">
          <div class="index5" v-for="(item,index) in line">
            <img style="width:100%;height:250px" :src="item.images.split(',')[0]">
            <div class="index6">
              <div class="index7">{{item.name}}</div>
              <div class="index8">{{item.introduce}}</div>
              <div class="index9" style="margin-top:7px;cursor: pointer"@click="toLineInfo(item.id)">
                查看
              </div>
            </div>
          </div>
        </div>
        <el-button style="margin-top:35px" size="small" type="primary" plain @click="toLine">了解更多</el-button>
      </div>
    </div>
    <bottoms></bottoms>
  </div>
</template>

<script>
  import {getSysRotationsList,getSysAttractionsIndex,getUserCount,getSysLineIndex,getSysHotelsIndex} from '../../api/api'
  import headers from '@/components/header'
  import bottoms from '@/components/bottom'
  export default {
    data() {
      return{
        hotels: [],
        rotations: [],
        count: 0,
        line: [],
      }
    },
    components: {
      headers,
      bottoms
    },
    methods: {
      toAttraction() {
        this.$router.push("/hotel")
      },
      toLine() {
        this.$router.push("/line")
      },
      toInfo(id) {
        this.$router.push("/hotelInfo?id=" + id)
      },
      toLineInfo(id) {
        this.$router.push("/lineInfo?id=" + id)
      },
      getSysRotationsList() {
        getSysRotationsList().then(res => {
          if (res.code == 1000) {
            this.rotations = res.data
          }
        })
      },
      getSysHotelsIndex() {
        getSysHotelsIndex().then(res => {
          if (res.code == 1000) {
            this.hotels = res.data
          }
        })
      },
      getUserCount() {
        getUserCount().then(res => {
          if (res.code == 1000) {
            this.count = res.data
          }
        })
      },
      getSysLineIndex() {
        getSysLineIndex().then(res => {
          if (res.code == 1000) {
            this.line = res.data
          }
        })
      }
    },
    created() {
     
    },
    mounted() {
      this.getSysRotationsList()
      this.getSysHotelsIndex()
      this.getUserCount()
      this.getSysLineIndex()
    }
 }
</script>

<style scoped>
   @import url('../../assets/css/index.css');
</style>