<template>
  <div class="bottom">
    <div class="bottom1">
      <img style="width: 750px;height: 300px" src="../assets/image/406be3340d894a959aeac03757219a8a (2).png">
    </div>
    <div class="bottom2">
      <div style="font-size:20px">AI课程知识点推荐系统</div>
      <div>本系统需实现用户注册与登录、课程浏览与评分、行为数据记录与管理、以及个性化推荐结果展示等核心功能模块，确保用户能够便捷地使用平台并获得高质量的学习资源推荐。系统在推荐逻辑上需支持基于协同过滤算法的兴趣建模，结合用户历史评分与相似用户行为，实现个性化课程推荐。</div>
    </div>
    <div class="bottom3">
      <div style="font-size:20px">联系我们：</div>

      <div>邮箱: gwg123@gmail.com</div>
      <div>QQ: 2940247949</div>

      <div>电话: 19813498303</div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return{

      }
    },
    methods: {
      toPage(name) {
        if (name == "index") {
          this.$router.push("/")
        } else if (name == "vegetable") {
          this.$router.push("/vegetable")
        } else if (name == "information") {
          this.$router.push("/information")
        } else if (name == "shed") {
          this.$router.push("/shed")
        } else if (name == "leave") {
          this.$router.push("/leave")
        } else if (name == "manage") {
          window.open("http://localhost:3000")
        } else if (name == "car") {
          this.$router.push("/car")
        } else if (name == "favor") {
          this.$router.push("/favor")
        } else if (name == "order") {
          this.$router.push("/order")
        } else if (name == "appointment") {
          this.$router.push("/appointment")
        }
      }
    },
 }
</script>

<style scoped>
  .bottom {
      width: 100%;
      height: 300px;
      display: flex;
      justify-content: space-around;
      background-color: #a0b8d6;
      align-items: center;
      font-family: '黑体';
  }
  .bottom1,.bottom3,.bottom2 {
    color: #000000;
  }
  .bottom2 {
    width: 500px;
    letter-spacing: 2px;
    line-height: 30px;
  }
  .bottom1>div,.bottom3>div,.bottom2>div {
    font-size: 14px;
    margin-top: 12px;
    cursor: pointer;
  }
</style>
