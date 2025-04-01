import React, { useEffect, useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import styles from './Background.module.css';

const backgrounds = [
  {
    url: '/images/backgrounds/alps.jpg',
    description: '瑞士阿尔卑斯山脉'
  },
  {
    url: '/images/backgrounds/banff.jpg',
    description: '加拿大班夫国家公园'
  },
  {
    url: '/images/backgrounds/yosemite.jpg',
    description: '美国优胜美地国家公园'
  },
  {
    url: '/images/backgrounds/aurora.jpg',
    description: '冰岛极光'
  },
  {
    url: '/images/backgrounds/milford.jpg',
    description: '新西兰米尔福德峡湾'
  },
  {
    url: '/images/backgrounds/maldives.jpg',
    description: '马尔代夫环礁'
  },
  {
    url: '/images/backgrounds/grand-canyon.jpg',
    description: '美国大峡谷'
  }
];

const Background: React.FC = () => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % backgrounds.length);
    }, 20000);

    return () => clearInterval(timer);
  }, []);

  return (
    <div className={styles.backgroundContainer}>
      <AnimatePresence mode="wait">
        <motion.div
          key={currentIndex}
          className={styles.backgroundImage}
          initial={{ opacity: 0, scale: 1.1 }}
          animate={{ opacity: 1, scale: 1 }}
          exit={{ opacity: 0, scale: 0.9 }}
          transition={{ 
            duration: 2,
            ease: "easeInOut"
          }}
          style={{
            backgroundImage: `url(${backgrounds[currentIndex].url})`
          }}
        >
          <div className={styles.overlay} />
          <div className={styles.imageDescription}>
            {backgrounds[currentIndex].description}
          </div>
        </motion.div>
      </AnimatePresence>
    </div>
  );
};

export default Background; 