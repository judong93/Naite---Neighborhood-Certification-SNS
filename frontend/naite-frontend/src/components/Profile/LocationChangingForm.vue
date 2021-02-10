<template>
  <div id="location-changing-form">
    <form action="#">
      <label for="#">주소</label><br>
      <input type="text" 
      v-model="userBasicAddress" @click="openSearchLocation" 
      placeholder="클릭하여 주소를 검색해주세요" readonly><br>
      <label for="#">상세주소</label><br>
      <input type="text"
      v-model="userDetailAddress" ><br>
      <button class="setting-btn">확인</button>
    </form>
    <Location :searchLocation='searchLocation' @selectAddress = 'selectAddress' @checkAddress='checkAddress' />
  </div>

</template>

<script>
import Location from '@/components/Sign/Location'

export default {
  name: 'LocationChangingForm',
  data: function () {
    return {
      userBasicAddress: '',
      userDetailAddress: '',
      userDong: '',
      searchLocation: false,
      addressConfirm: false,
    }
  },
  components:{
    Location,                
  },
  methods: {
    checkSpace:function () {
    event.returnValue=false;
    },
    openSearchLocation:function(){
      if (this.searchLocation) {
          this.searchLocation = false
      } else {
          this.searchLocation= true
      }
    },
    selectAddress:function(result) {
      this.searchLocation = false
      this.userBasicAddress = result.address
      this.userDong = result.bname2
      console.log(this.userBasicAddress)
      console.log(this.userDong)
    },
    checkAddress:function(res) {
      this.addressConfirm = res
    },  
  }

}
</script>

<style>
#location-changing-form::placeholder {
  color: black;
}
</style>