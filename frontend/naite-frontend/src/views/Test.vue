<template>
  <div>
    <Navbar />
    <BoardImg :messagetitle='messagetitle' :message='message' :imgsrc='imgsrc' :onDetail='onDetail'/>
    <div style="display: flex; justify-content: center;">
      <div class="group-buying">
        <div v-for="(posting, idx) in postings" :key="idx" class="posting-box">
          <img :src="posting.thumbnail" alt="" class="posting-img">
          <div class="posting-card">
            {{ posting.title }} {{ posting.area }} {{ posting.created }}
          </div>
        </div>
      </div>
    </div>
    <Message />
  </div>
</template>
<script>
import Navbar from '@/components/Navbar'
import Message from '@/components/Message'
import BoardImg from '@/components/BoardImg'
export default {
  name: "GroupBuying",
  data: function () {
    return {
      messagetitle:'나의 이웃테두리: 나이테',
      message:'당신이 모르는 동네이야기',
      imgsrc: 'boardimg-main.png',
      onDetail: false,
      postings: [
        {title: "뿌링클 한마리 나누실분!!", area: "동대문구 이문동", created: "15분 전", thumbnail: "https://picsum.photos/200/300"},
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
<style>
.group-buying {
  position: relative;
  overflow: auto;
  top: 280px;
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
  display: flex;
  width: 765px;
  margin: 3px;
  
  border-bottom: 2px solid lightgray;
}
.posting-card {
  margin-left: 20px;
}
.posting-img {
  height: 120px;
  width: 120px;
  overflow: hidden;
}
</style>