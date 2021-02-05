<template>
  <div id="market-board">
    
    <BoardImg :message='message' :imgsrc='imgsrc'/>
    <div style="display: flex; justify-content: center;">
      <div class="group-buying">
        <div v-for="(posting, idx) in postings1" :key="idx" class="posting-box">
          <img :src="posting.thumbnail" alt="" class="posting-img">
          <div class="posting-card">  
            <div class="posting-title">
              {{ posting.title }}
            </div>
            <div class="area-created">
              <div>
                가격: {{ posting.area }}원
              </div>
              <div class="created">
                {{ posting.created }}
              </div>
              <div>
                <i class="market-thumbs far fa-thumbs-up" >1</i>
                <i class="market-comments far fa-comment-dots">0</i>
              </div>
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

// const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "GroupBuying",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: '5',
      onDetail: false,
      postings1: [
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: 10000, created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
      ],
      marketPostings: [],    
    }
  },
  components: {
    BoardImg,
  },
  created: function () {
    axios.get('http://i4a402.p.ssafy.io:8080/market/list')
      .then((res) => {
        this.marketPostings = res.data
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
  height: 100px;
  width: 545px;
  margin-right: 20px;
  margin-bottom: 15px;
  border-bottom: 2px solid lightgray;
}
.posting-card {
  display: inline-block;
  /* margin-left: 20px; */
}
.posting-img {
  position: absolute;
  display: inline-block;
  height: 99%;
  width: 110px;
  overflow: hidden;
  left: 10px;
}
.posting-title {
  position: absolute;
  cursor: pointer;
  top: 3px;
  font-size: 25px;
  left: 180px;
}
.area-created {
  position: absolute;
  display: flex;
  bottom: 7px;
  left: 180px;
  width: 450px;
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
</style>