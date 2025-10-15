func findMedianSortedArrays(nums1 []int, nums2 []int) float64 {
    m, n := len(nums1), len(nums2)
    p1, p2 := 0, 0

    getMin := func() int {
        if p1 < m && p2 < n {
            if nums1[p1] < nums2[p2] {
                p1++
                return nums1[p1-1]
            } else {
                p2++
                return nums2[p2-1]
            }
        } else if p1 < m {
            p1++
            return nums1[p1-1]
        } else {
            p2++
            return nums2[p2-1]
        }
        return -1
    }

    var median float64
    if (m+n)%2 == 0 {
        for i := 0; i < (m+n)/2-1; i++ {
            getMin()
        }
        median = float64(getMin()+getMin()) / 2
    } else {
        for i := 0; i < (m+n)/2; i++ {
            getMin()
        }
        median = float64(getMin())
    }

    return median
}
