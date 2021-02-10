<template>
  <div id="marketboarddetail">
    <BoardImg :message='message' :imgsrc='imgsrc'/>
    <div class="market-detail-box">
      <div class="market-detail-img">
        <div class="market-detail-lg-img-container">
          <img :src="imgData" class="market-detail-lg-img" alt="">
        </div>
        <div class="market-detail-sm-img-container">
          <img :src="marketImg" v-for="(marketImg, idx) in imgs" :key="idx" alt="" class="market-detail-sm-img">
        </div>
      </div>
      <div class="market-detail-content-container">
        <div class="market-detail-content-title">{{marketDetailContent.board.boardTitle}}</div>
        <div class="market-detail-subs">
          <div class="subs-title">
            <p>작성자</p>
            <p>작성시각</p>
            <p>가격</p>
            <p>상세내용</p>
          </div>
          <div class="subs-content">
            <div class="market-detail-writer">
              <p @click="toWriterProfile">{{marketDetailContent.userNick}}</p>
              <i class="far fa-comment-dots fa-2x"><span>작성자와 채팅하기</span></i>
            </div>
            <p>{{marketDetailContent.boardCreatedAt}}</p>
            <p>{{marketDetailContent.marketCost}}원</p>
          </div>
        </div>
        <div class="market-detail-boardcontent">
          {{ marketDetailContent.board.boardContent }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BoardImg from '@/components/Board/BoardImg'
import axios from 'axios'

const SERVER_URL = 'https://i4a402.p.ssafy.io/api'

export default {
  name: 'MarketBoardDetail',
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: require(`../../assets/5.jpg`),
      imgData: require("../../assets/firstpage.jpg"),
      marketNo: 0,
      imgs: [
        require("../../assets/firstpage.jpg"),
        require("../../assets/firstpage.jpg"),
        require("../../assets/firstpage2.jpg"),
        require("../../assets/firstpage3.jpg"),
      ],
      marketDetailContent: [],
    }
  },
  components: {
    BoardImg,
  },
  created: function () {
    const marketNo = this.$route.params.marketNo
    this.marketNo = marketNo
    axios.get(`${SERVER_URL}/market/detail/${marketNo}/`)
      .then((res) => {
        this.marketDetailContent = res.data
        console.log(this.marketDetailContent)
      })
      .catch((err) => {
        console.log(err)
      })
  }
}
</script>

<style scoped>
#marketboarddetail {
  position:relative;
  width: 1920px;
  height: 969px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.market-detail-box {
  position: absolute;
  display: flex;
  top: 26%;
  /* border: solid 3px red; */
  height: 650px;
  width: 54%;
  left: 23%;
}
.market-detail-img {
  position: relative;
  overflow: hidden;
  /* border: solid 3px red; */
  width: 43%; 
}
.market-detail-content-container {
  display: flex;
  flex-direction: column;
  /* border: solid 3px greenyellow; */
  width: 57%;
  padding-left: 5%;
  text-align: left;
}
.market-detail-lg-img-container {
  position: relative;
  width: 100%;
  height: 70%;
  /* margin-top: 10%; */
}
.market-detail-lg-img {
  left: 0;
  position: relative;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.market-detail-sm-img-container {
  position: relative;
  height: 10%;
  width: 100%;
  margin-top: 5%;
  text-align: left;
}
.market-detail-sm-img {
  position: relative;
  height: 100%;
  width: 15%;
  margin-top: 5%;
  margin-right: 4%;
}
.market-detail-content-title {
  border-bottom: 1px solid rgb(201, 197, 197);
  padding-bottom: 10px;
  font-size: 25px;
  margin-bottom: 20px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
.market-detail-subs {
  display: flex;
}
.subs-title {
  position: relative;
  color: rgb(184, 182, 182);
  width: 23%;
  padding-top: 5px;
}
.subs-content {
  position: relative;
  /* display: flex; */
  width: 70%;
  /* padding-top: 5px; */
  /* margin-right: 100px; */
}
.market-detail-writer {
  display: flex;
}
.market-detail-writer > p {
  padding-top: 5px;
  cursor: pointer;
}
.fa-comment-dots {
  position: relative;
  margin-left: 30px;
  /* padding-bottom: 20px; */
  /* padding-top: 0; */
}
.market-detail-boardcontent {
  margin-top: 4px;
  height: 34%;
  width: 100%;
  overflow: auto;
  padding: 1% 1% 1% 0;
}
.far {
  cursor: pointer;
}
.far:hover span {
  display: inline-block;
}
.far span {
  display: none;
  font-size: 15px;
  font-family: font1;
}
</style>