<template>
  <div id="market-board">
    <Navbar />
    <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
    <div style="display: flex; justify-content: center;">
      <div class="group-buying">
        <div v-for="(posting, idx) in marketPostings" :key="idx" class="posting-box">
          <img :src="thumbnail" alt="" class="posting-img">
          <div class="posting-card">  
            <div class="posting-title">
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
                <i class="market-thumbs far fa-thumbs-up" >1</i>
                <i class="market-comments far fa-comment-dots">0</i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <Message />
  </div>
</template>

<script>
import Navbar from '@/components/Basic/Navbar'
import Message from '@/components/Basic/Message'
import BoardImg from '@/components/Board/BoardImg'
import axios from 'axios'

// const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "MarketBoard",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: 'boardimg-main.png',
      onDetail: false,
      thumbnail: "https://picsum.photos/200/300",
      marketPostings: [],
    }
  },
  components: {
    Navbar,
    Message,
    BoardImg,
  },
  created: function () {
    axios.get('http://i4a402.p.ssafy.io:8080/market/list')
      .then((res) => {
        this.marketPostings = res.data
        console.log(this.marketPostings)
      })
      .catch((err) => {
        console.log(err)
      })
  },
}
</script>

<style scoped>
#market-board {
  position:relative;
  width: 1918px;
  height: 935px;
  overflow:hidden;
  background-color: rgb(250, 246, 240);
  font-family: font1;
}
.group-buying {
  position: absolute;
  overflow: auto;
  top: 26%;
  height: 700px;
  width: 60%;
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