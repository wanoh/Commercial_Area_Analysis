<template>
  <div id='bz-select-box'>
    <div id='bz-select-box-selected'  @click='toggleBox' >{{ selected }}</div>
    <div id='bz-select-box-list' :style='listStyle' v-animation>
      <ul>
        <li @click='selectItem(-1)'>전체</li>
        <li v-for='(item, num) in list' :key='item.num' @click='selectItem(num)'>{{ item }}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import { eventBus } from '@/js/bus'

export default {
  data () {
    return {
      selected: '전체',
      listStyle: {
        display: 'none'
      },
      name: null
    }
  },
  props: {
    list: Array,
    init: Boolean,
    type: String
  },
  methods: {
    toggleBox () {
      if (this.listStyle.display === 'block') {
        this.listStyle.display = 'none'
      } else {
        this.listStyle.display = 'block'
      }
    },
    selectItem (num) {
      if (num === -1) {
        this.selected = '전체'
      } else {
        this.selected = this.list[num]
      }
      this.listStyle.display = 'none'
    }
  },
  watch: {
    selected () {
      if (this.type === 'large') {
        this.$emit('large-event', this.selected)
      } else if (this.type === 'mid') {
        this.$emit('mid-event', this.selected, false)
        eventBus.$emit('cateSelected')
        this.$store.state.subCategory = this.selected
      }
    }
  },
  updated () {
    if (this.init) {
      this.selected = '전체'
      this.init = false
    }
  }
}
</script>

<style scoped src='./SelectBox.scss' lang='scss'></style>
