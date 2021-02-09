<template>
  <div id="market-board">
    
    <BoardImg :message='message' :imgsrc='imgsrc'/>
    <div class="button-list">
      <select name="카테고리" v-model="smallCategoryNo">
        <option value="6" selected>공동구매</option>
        <option value="7">중고거래</option>
      </select>
      <span class='write-button' @click='writeBoard'>글 쓰기 </span>
    </div>
    <div class="group-buying">
      <div @click="toMarketDetail(posting.marketNo)" v-for="(posting, idx) in marketPostings" :key="idx" class="posting-box">
        <img :src="thumbnail" alt="" class="posting-img">
        <div class="posting-card">  
          <div  class="posting-title">
            {{ posting.board.boardTitle }}
          </div>
          <div class="area-created">
            <div>
              가격: {{ posting.marketCost }}원
            </div>
            <!-- <div class="created">
              {{ posting.board.boardCreatedAt }} 
            </div> -->
            <div class="marks">
              <i class="market-thumbs far fa-thumbs-up" >0</i>
              <i class="market-comments far fa-comment-dots">0</i>
            </div>
          </div>
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
  name: "MarketBoard",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: require(`../../assets/5.jpg`),
      onDetail: false,
      thumbnail: "https://picsum.photos/200/300",
      marketPostings: [],
      smallCategoryNo: 6,
    }
  },
  components: {
    BoardImg,
  },
  methods: {
    writeBoard:function(){
      this.$router.push({name:'Posting'})
    },
    toMarketDetail: function (marketNo) {
      this.$router.push({name:'MarketBoardDetail',params:{marketNo:marketNo}})
    },
  },
  created: function () {
    axios.get(`${SERVER_URL}/market/list/6/`)
      .then((res) => {
        this.marketPostings = res.data
        console.log(this.marketPostings)
      })
      .catch((err) => {
        console.log(err)
      })
  },
  watch: {
    smallCategoryNo: function () {
      const smallCategoryNo = this.smallCategoryNo
      axios.get(`${SERVER_URL}/market/list/${smallCategoryNo}/`)
        .then((res) => {
          this.marketPostings = res.data
          console.log(this.marketPostings)
        })
        .catch((err) => {
          console.log(err)
        })
    }
  }, 
}
</script>

<style scoped>
#market-board {
  position:relative;
  width: 1920px;
  height: 969px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.button-list {
  position:absolute;
  top: 24%;
  left:19.7%;
  width: 60%;
  height: 2%;
  text-align: right;
}
.write-button {
  cursor:pointer;
  font-size: 20px;
  background-color: #3F9F47;
  color:white;
  padding: 5px 15px;
  border-radius: 10px;
}
.group-buying {
  position: absolute;
  /* display: flex; */
  /* flex-wrap: wrap; */
  overflow: auto;
  top: 29%;
  height: 700px;
  width: 60%;
  left: 20%;
  right: 20%;
  text-align: left;
}
.vl {
  border: 1px solid lightgray;
  height: 500px;
  position: absolute;
  left: 50%;
  top: 300px;
}
.posting-box {
  position: relative;
  display: inline-block;
  overflow: hidden;
  height: 110px;
  width: 545px;
  margin-right: 20px;
  margin-bottom: 10px;
  border-bottom: 2px solid lightgray;
  left: 0;
}
.posting-card {
  display: inline-block;
  /* margin-left: 20px; */
}
.posting-img {
  position: absolute;
  display: inline-block;
  height: 93%;
  width: 19.5%;
  overflow: hidden;
  left: 10px;
  border-radius: 10%;
  margin-bottom: 15px;
}
.posting-title {
  position: absolute;
  overflow: hidden;
  cursor: pointer;
  width: 60%;
  top: 3px;
  font-size: 25px;
  left: 180px;
  white-space: nowrap;
  text-overflow: ellipsis;
  text-align: left;
}
.area-created {
  position: absolute;
  display: flex;
  bottom: 7px;
  left: 180px;
  width: 60%;
}
.created {
  /* position: absolute; */
  margin-left: 60px;
}
.market-thumbs {
  margin-left: 40px;
}
.market-comments {
  margin-left: 17px;
}
.marks {
  position: absolute;
  right: 0;
}
</style>