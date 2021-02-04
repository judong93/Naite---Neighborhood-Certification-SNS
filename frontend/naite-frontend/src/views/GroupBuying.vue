<template>
  <div>
    <Navbar />
    <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
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
                {{ posting.area }}
              </div>
              <div class="created">
                {{ posting.created }}
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

export default {
  name: "GroupBuying",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: 'boardimg-main.png',
      onDetail: false,
      postings1: [
        {title: "뿌링클 한마리 나누실분!!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
        {title: "아기헤어밴드 팝니다!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
      ],    
    }
  },
  components: {
    Navbar,
    Message,
    BoardImg,
  }
}
</script>

<style scoped>
.group-buying {
  position: relative;
  overflow: auto;
  top: 285px;
  height: 600px;
  width: 1530px;
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
  height: 120px;
  width: 700px;
  margin: 0 20px;
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
  height: 110px;
  width: 110px;
  overflow: hidden;
  left: 10px;
}
.posting-title {
  position: absolute;
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
  position: absolute;
  right: 0;
}
</style>