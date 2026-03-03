<template>
  <div class="attractionsInfo">
    <headers></headers>
    <div class="attractionsInfo1">
        <div class="attractionsInfo2">
            <div class="attractionsInfo3">
                <el-carousel height="500px">
                    <el-carousel-item v-for="(item,index) in info.images.split(',')" :key="index">
                        <img style="width:100%;height:100%" :src="item">
                    </el-carousel-item>
                </el-carousel>
            </div>
            <div class="attractionsInfo4">
                <div class="attractionsInfo5">{{info.name}}</div>
                <div class="" style="margin-top: 10px;margin-left: 20px;font-size: larger;font-weight: normal">所属学科：{{info.attractions}}</div>
                <div class="attractionsInfo6" style="white-space: pre-line;font-weight: bold">{{info.introduce}}</div>
                <div class="attractionsInfo7" style="margin-left:10px">
                    <el-button style="margin-top: 50px" size="small" type="primary" plain @click="toOrder">规划学习知识</el-button>
                </div>
            </div>
        </div>
    </div>
    <div class="forumInfo1">
      <div class="forumInfo2">
        <div class="forumInfo4" style="font-family: 'Microsoft YaHei'" v-html="info.content">
        </div>
      </div>
    </div>
    <div class="attractionsInfo1">
      <div class="attractionsInfo8">
        <el-input style="margin-top:20px" v-model="content1" type="textarea" rows="7" placeholder="请输入评论内容"></el-input>
        <el-rate v-model="score"></el-rate>
        <el-button style="margin-top:20px" type="primary" size="small" plain @click="saveSysHotelsComments">评论</el-button>
        <div class="forum1" style="width:100%">
          <div class="forum2" style="padding:0" v-for="(item,index) in tableData" :key="index">
            <img style="border-radius:50%;width:40px;height:40px;margin-left:20px" :src="$store.state.HOST + item.avatar">
            <div style="margin-left:10px">
              {{item.createBy}}
            </div>
            <div style="margin-left:20px">
              {{item.content}}
            </div>
          </div>
        </div>
        <el-pagination
            background
            :page-size="search.pageSize"
            layout="prev, pager, next"
            @current-change="handleCurrentChange"
            :total="total">
        </el-pagination>
      </div>
    </div>
    <el-dialog
    title="规划学习知识"
    :visible.sync="dialogVisible"
    width="40%">
    <span>
        <div>
            <el-radio style="margin-top:10px" v-for="(item,index) in hotel" :key="index" size="small" v-model="radio1" :label="item.id" border>{{item.name}} - {{item.price}}分钟</el-radio>
        </div>
        <el-input-number style="margin-top:10px" size="small" v-model="num" :min="1" :max="10"></el-input-number>
        <el-date-picker size="small" style="margin-left:20px;margin-top:10px"
        v-model="date1"
        type="date"
        value-format="yyyy-MM-dd"
        placeholder="选择学习日期">
        </el-date-picker>
        <div class="attractionsInfo9">
            <el-input size="small" v-model="people.name" placeholder="请输入姓名"></el-input>
            <el-input size="small" style="margin-left:10px" v-model="people.tel" placeholder="请输入电话"></el-input>

        </div>
    </span>
    <span slot="footer" class="dialog-footer">
        <el-button @click="closeOrder" size="small">取 消</el-button>
        <el-button type="primary" @click="saveOrder"  size="small">确 定</el-button>
    </span>
    </el-dialog>
    <bottoms></bottoms>
  </div>
</template>

<script>
import {getSysHotelById, getSysHotelItemList, saveSysHotelOrder, getSysHotelsCommentsPage, saveSysHotelsComments} from '../../api/api'
  import headers from '@/components/header'
  import bottoms from '@/components/bottom'
  export default {
    data() {
      return{
        id: "",
        dialogVisible: false,
        content: "",
        content1: "",
        total:100,
        tableData: [],
        search: {
          hotelsId: "",
          pageSize: 10,
          pageNumber: 1,
        },
        date1: "",
        num: "",
        score: null,
        userId: null,
        people:{
            name: "",
            tel: "",
            idCard: ""
        },
        info: {},
        hotel: [],
        radio1: ""
      }
    },
    components: {
      headers,
      bottoms
    },
    methods: {
      saveSysHotelsComments() {
        if (!this.content1) {
          this.$message({
            message: '请输入评论内容',
            type: 'warning'
          });
          return
        }
        var param = {
          content: this.content1,
          score:this.score,
          hotelsId: this.id,
          userId:this.userId
        }
        saveSysHotelsComments(param).then(res => {
          if (res.code == 1000) {
            this.$message({
              message: '评论成功',
              type: 'success'
            });
            this.content1 = ""
            this.getSysHotelsCommentsPage()
          }
        })
      },
      getSysHotelsCommentsPage() {
        this.search.hotelsId = this.id
        getSysHotelsCommentsPage(this.search).then(res => {
          if (res.code == 1000) {
            this.tableData = res.data.records
            this.total = res.data.total
          }
        })
      },
      getSysHotelItemList() {
        getSysHotelItemList({id:this.id}).then(res => {
          if (res.code == 1000) {
            this.hotel = res.data
          }
        })
      },
      closeOrder() {
        this.date1 = ""
        this.num = ""
        this.radio1 = ''
        this.people = 
        {
          name: "",
          tel: "",
          idCard: ""
        }
        this.dialogVisible = false
      },
      saveOrder() {
          if (!this.date1) {
            this.$message({
                message: '请选择预约时间',
                type: 'warning'
            });
            return
          }
          if (!this.people.name) {
              this.$message({
                  message: '请完善预约人姓名',
                  type: 'warning'
              });
              return
          }
          if (!this.people.tel) {
              this.$message({
                  message: '请完善预约人联系方式',
                  type: 'warning'
              });
              return
          }

          if (!this.radio1 ) {
              this.$message({
                  message: '请选择房型',
                  type: 'warning'
              });
              return
          }
          var param = {
            hotelId: this.id,
            num: this.num,
            itemId: this.radio1,
            time: this.date1,
            people: JSON.stringify(this.people)
          }
          saveSysHotelOrder(param).then(res => {
              if (res.code == 1000) {
                this.$message({
                    message: '规划学习成功，请等待确认',
                    type: 'success'
                });
                this.closeOrder()
              } else {
                this.$message({
                    message: res.message,
                    type: 'warning'
                });
              }
          })
      },
      getSysHotelById() {
        getSysHotelById({id:this.id}).then(res => {
          if (res.code == 1000) {
            this.info = res.data
          }
        })
      },
      toOrder() {
        this.dialogVisible = true
      },
    },
    created() {
     
    },
    mounted() {
      this.id = this.$route.query.id
      this.userId = JSON.parse(window.localStorage.getItem("user_info")).id
      this.getSysHotelById()
      this.getSysHotelItemList()
      this.getSysHotelsCommentsPage()
    }
 }
</script>

<style scoped>
   @import url('../../assets/css/attractionsInfo.css');
</style>