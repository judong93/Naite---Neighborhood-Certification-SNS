<template>
    <div id="location">
    
        <div v-if='searchLocation'>
            <VueDaumPostcode
            style='width:500px;max-height:700px; backgroudColor:red;position:fixed; top:50%;left:50%; overflow:auto;transform: translate(-50%,-50%);borderRadius:30px;' 
            id = 'locationSearch'
            @complete='result=$event'
            @click='checkAddress'
            :theme="{
                bgColor: '#162525',
                searchBgColor: '#162525',
                contentBgColor: '#162525',
                pageBgColor: '#162525',
                textColor: '#FFFFFF',
                queryTextColor: '#FFFFFF',
                outlineColor: '#44444'
            }"
            />
            
        </div>
    </div>
</template>

<script>
import {VueDaumPostcode} from 'vue-daum-postcode'
// import axios from 'axios'

export default {
    name:'Location',
    components:{
        VueDaumPostcode,
                
    },
    data: function() {
        return {
            serverLatitude:0,
            serverLongitude:0,  
            userLatitude:0,
            userLongitude:0,
            result:'',    
            userBname:'',
        }
    },
    methods:{  
        geo: function(){
            let that = this
            var geocoder = new window.kakao.maps.services.Geocoder();
            var callback = function(result,status) {
                if (status === 'OK') {
                    that.userLatitude = result[0].address.y
                    that.userLongitude= result[0].address.x
                }   
            }
            geocoder.addressSearch(this.userBname,callback)          
        },
    },
    computed: {
    },
    props:{
        searchLocation:Boolean,
    },
    watch: {
        searchLocation:function(){
            let that = this
            if (navigator.geolocation && this.searchLocation) {
                navigator.geolocation.getCurrentPosition(function(position) {       
                that.serverLatitude = position.coords.latitude
                that.serverLongitude = position.coords.longitude
                
                });
            } else {
                if (!that.result) {
                    alert('잠시후 다시 이용해주세요')
                }
            }
            
        },
        result: function(result){
            this.userBname = result.bname
            this.geo()
            this.$emit('selectAddress',result)            
        },
        userLatitude:function(){
            const a = Math.pow(this.serverLatitude-this.userLatitude,2)
            const b = Math.pow(this.serverLongitude-this.userLongitude,2)
            if (Math.sqrt(a+b)<=0.01) {
                console.log('가')
                this.$emit('checkAddress',true)
            } else {
                console.log('멈')
                this.$emit('checkAddress',false)
                
            }
        }
    },
    mounted(){
        if (window.kakao && window.kakao.maps) {
            console.log('Welcome naite!')
        } else {
            const script = document.createElement('script');
            script.src= 'http://dapi.kakao.com/v2/maps/sdk.js?appkey=e990ec2518ab112eae22d81bcd858753&libraries=services'
            document.head.appendChild(script)
        }
    }

    
}
</script>

<style>
#location {
    position: relative;
    width: 200px;
    height:200px;
    background-color: transparent;
    z-index: 3;
    transform: trnaslate(-50%,-50%);
}

#locationSearch::-webkit-scrollbar{
    display: none;
}

</style>
