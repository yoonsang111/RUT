/** @type {import('next').NextConfig} */
const nextConfig = {
  images: {
    unoptimized: true,
    imageSizes: [16, 32, 48, 64, 96, 128, 256, 384],
    deviceSizes: [640, 750, 828, 1080, 1200, 1920, 2048, 3840],
  },
  output: "export",
  trailingSlash: true,
};

export default nextConfig;
