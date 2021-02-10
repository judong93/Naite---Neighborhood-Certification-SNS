<template>
    <div id="maincategory">
        <ReviewBoardImg :imgsrc='imgsrc'/>
        <ReviewBoardList :bigCategoryNo='bigCategoryNo'/>
    </div>
    
</template>
<script>

import ReviewBoardImg from '@/components/ReviewBoard/ReviewBoardImg'
import ReviewBoardList from '@/components/ReviewBoard/ReviewBoardList'

export default {
    name:'ReviewBoard',
    components:{
        ReviewBoardImg,
        ReviewBoardList,
    },
    data: function() {
        return {
            bigCategoryNo:3,
            message:'당신이 모르는 동네이야기',
            imgsrc: require(`../../assets/3.jpg`),                        
            boardNo: 0,
            
        }
    },
    methods:{     
    },
    computed: {

    },
    watch: {
    },

}
</script>

<style>
#maincategory {
    position:relative;
    width: 1920px;
    height:969px;
    overflow:hidden;
    background-color: rgb(250, 246, 240);
}

</style>