/**
 * Median of Two Sorted Arrays
 * 
 * Approach: Binary Search on Partition
 * Time: O(log(min(m, n)))
 * Space: O(1)
 * 
 * Key Idea:
 * Partition both arrays such that:
 * 1. Left partition has (m+n+1)/2 elements
 * 2. All left elements ≤ all right elements
 */

class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Always binary search on the smaller array for efficiency
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int low = 0;
        int high = m;
        
        while (low <= high) {
            // Partition position in nums1
            int cut1 = (low + high) / 2;
            // Partition position in nums2 (ensure left side has (m+n+1)/2 elements)
            int cut2 = (m + n + 1) / 2 - cut1;
            
            // Get the four boundary values
            // Use Integer.MIN_VALUE/MAX_VALUE for edge cases
            int left1 = (cut1 == 0) ? Integer.MIN_VALUE : nums1[cut1 - 1];
            int right1 = (cut1 == m) ? Integer.MAX_VALUE : nums1[cut1];
            int left2 = (cut2 == 0) ? Integer.MIN_VALUE : nums2[cut2 - 1];
            int right2 = (cut2 == n) ? Integer.MAX_VALUE : nums2[cut2];
            
            // Check if this partition is valid
            if (left1 <= right2 && left2 <= right1) {
                // Valid partition found!
                // Calculate median based on whether total length is odd or even
                if ((m + n) % 2 == 0) {
                    // Even total: average of two middle elements
                    return (Math.max(left1, left2) + Math.min(right1, right2)) / 2.0;
                } else {
                    // Odd total: the larger element on left side
                    return Math.max(left1, left2);
                }
            } 
            // Move cut1 left if left1 is too large
            else if (left1 > right2) {
                high = cut1 - 1;
            } 
            // Move cut1 right if left2 is too large
            else {
                low = cut1 + 1;
            }
        }
        
        return -1.0; // Should never reach here if inputs are valid
    }
}

/*
 * DETAILED DRY RUN
 * ================
 * 
 * Example 1: nums1 = [1,3], nums2 = [2]
 * m = 2, n = 1, total = 3 (odd)
 * 
 * Initial: low = 0, high = 2
 * 
 * ITERATION 1:
 *   cut1 = 1
 *   cut2 = (2 + 1 + 1) / 2 - 1 = 2 - 1 = 1
 *   
 *   left1 = nums1[0] = 1
 *   right1 = nums1[1] = 3
 *   left2 = nums2[0] = 2
 *   right2 = MAX_VALUE
 *   
 *   Check: 1 <= MAX_VALUE ✓ AND 2 <= 3 ✓
 *   VALID PARTITION!
 *   
 *   Total is odd (3):
 *   median = max(1, 2) = 2 ✓
 * 
 * Output: 2.0
 * 
 * ---
 * 
 * Example 2: nums1 = [1,2], nums2 = [3,4]
 * m = 2, n = 2, total = 4 (even)
 * 
 * Initial: low = 0, high = 2
 * 
 * ITERATION 1:
 *   cut1 = 1
 *   cut2 = (2 + 2 + 1) / 2 - 1 = 2 - 1 = 1
 *   
 *   left1 = nums1[0] = 1
 *   right1 = nums1[1] = 2
 *   left2 = nums2[0] = 3
 *   right2 = nums2[1] = 4
 *   
 *   Check: 1 <= 3 ✓ but 3 > 2 ✗
 *   left1 > right2 is false, left2 > right1 is TRUE
 *   Move left: low = cut1 + 1 = 2
 * 
 * ITERATION 2:
 *   cut1 = 2
 *   cut2 = (2 + 2 + 1) / 2 - 2 = 2 - 2 = 0
 *   
 *   left1 = nums1[1] = 2
 *   right1 = MAX_VALUE
 *   left2 = MIN_VALUE
 *   right2 = nums2[0] = 3
 *   
 *   Check: 2 <= 3 ✓ AND MIN_VALUE <= MAX_VALUE ✓
 *   VALID PARTITION!
 *   
 *   Total is even (4):
 *   median = (max(2, MIN_VALUE) + min(MAX_VALUE, 3)) / 2.0
 *          = (2 + 3) / 2.0 = 2.5 ✓
 * 
 * Output: 2.5
 */

/*
 * VISUAL PARTITION EXPLANATION
 * =============================
 * 
 * For nums1 = [1,2], nums2 = [3,4], cut1=2, cut2=0:
 * 
 *    nums1: [1, 2] | (empty)
 *    nums2: (empty) | [3, 4]
 *           
 *           Left Side    Right Side
 *           {1, 2}       {3, 4}
 * 
 *    Invariant: max(left) = 2 ≤ min(right) = 3 ✓
 *    Median = (2 + 3) / 2 = 2.5
 * 
 * ---
 * 
 * For nums1 = [1,3], nums2 = [2], cut1=1, cut2=1:
 * 
 *    nums1: [1] | [3]
 *    nums2: [2] | (empty)
 *    
 *           Left Side    Right Side
 *           {1, 2}       {3}
 * 
 *    Invariant: max(left) = 2 ≤ min(right) = 3 ✓
 *    Median = 2 (odd total, so max of left)
 */

/*
 * WHY THIS WORKS
 * ==============
 * 
 * 1. Binary search on smaller array → O(log(min(m,n)))
 * 2. For each cut position in nums1, calculate cut in nums2
 *    to ensure left partition has exactly (m+n+1)/2 elements
 * 3. Check if max(left) ≤ min(right):
 *    - If yes: found the median
 *    - If left too large: move cut1 left
 *    - If right too large: move cut1 right
 * 4. Handle odd/even total lengths appropriately
 * 
 * KEY INSIGHT: The "correct" partition must satisfy:
 *   max(left partition) ≤ min(right partition)
 */